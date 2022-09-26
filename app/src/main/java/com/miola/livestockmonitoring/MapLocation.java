package com.miola.livestockmonitoring;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.miola.livestockmonitoring.databinding.ActivityMapBinding;
import com.miola.livestockmonitoring.helpers.CurrentUser;
import com.miola.livestockmonitoring.model.Cow;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MapLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapBinding binding;

    private int GPS_REQUEST_CODE = 9001;
    private int LOCATION_REQUEST_CODE = 1234;

    private FusedLocationProviderClient mLocation;
    private LocationRequest locationRequest;

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            if (mMap != null)
            {
                setUserLocationMarker(locationResult.getLastLocation());
            }
        }
    };

    private Marker userLocationMarker;

    private Map<String, Marker> cowsMarker = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkMyPermission();

        mLocation = new FusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    private void checkMyPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(MapLocation.this, "Permission Garanted", Toast.LENGTH_SHORT).show();
                initMap();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                startActivityForResult((new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)).setData(Uri.fromParts("package", getPackageName(), "")), LOCATION_REQUEST_CODE);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private void initMap() {
        if (isGPSEnable())
        {
            ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(MapLocation.this);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS")
                    .setPositiveButton("Yes", ((dialogInterface, i) -> {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
        }
    }

    private boolean isGPSEnable() {
        return ((LocationManager) getSystemService(LOCATION_SERVICE)).isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void getCurrLoc() {
        mLocation.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                Location location = task.getResult();

                if (location != null)
                {
                    gotoLocation(location.getLatitude(), location.getLongitude());
                }
            }
            else
            {

            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        mLocation.removeLocationUpdates(locationCallback);
    }

    private void setUserLocationMarker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (userLocationMarker == null)
        {
            // create a new marker
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.farmer_icon));
            //          markerOptions.rotation(location.getBearing());
            markerOptions.anchor((float) 0.5, (float) 0.5);
            userLocationMarker = mMap.addMarker(markerOptions);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
        else
        {
            // use pereviosely created marker
            userLocationMarker.setPosition(latLng);
            // for set rotatio of my icon
            //          userLocationMarker.setRotation(location.getBearing());
            // if you want that the camera move with your position
            //          mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        }
    }

    private void initCowsLocation(){
        for (Entry<String, Cow> entry : CurrentUser.getCurrentUser().getCows().entrySet()) {
            LatLng latLng = new LatLng(entry.getValue().getGps().getLatitude(), entry.getValue().getGps().getLongitude());
            cowsMarker.put(entry.getKey(), mMap.addMarker(new MarkerOptions()
                    .title(entry.getKey())
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.cow_icon))));
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);

        getCurrLoc();

        startLocationUpdates();

        initCowsLocation();

        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("cows").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    // i guess you should remove this two line because the current user is updated in the activity of sigin
                    //CurrentUser.setCurrentUser(dataSnapshot.getValue(User.class));
                    System.out.println(dataSnapshot.getValue());
                    HashMap<String, Cow> cows = new HashMap<>();

                    for(DataSnapshot cowsSnapshot : dataSnapshot.getChildren()){
                        Cow cow = cowsSnapshot.getValue(Cow.class);
                        String key = cowsSnapshot.getKey();
                        cows.put(key, cow);
                    }

                    CurrentUser.getCurrentUser().setCows(cows);
                    updateMarkers(CurrentUser.getCurrentUser().getCows());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void updateMarkers(Map<String, Cow> cows) {
        for (Entry<String, Cow> entry : cows.entrySet()) {
            Cow cow = (Cow) entry.getValue();
            LatLng latLng = new LatLng(cow.getGps().getLatitude(), cow.getGps().getLongitude());
            cowsMarker.get(entry.getKey()).setPosition(latLng);
        }
    }
}