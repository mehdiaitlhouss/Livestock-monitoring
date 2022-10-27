package com.miola.livestockmonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sensors extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;

    private Switch lightSensorAlarm;
    private Switch doorSensor;
    private Switch flameSensorAlarm;

    private ImageView lightImg;
    private ImageView flameImg;
    private ImageView doorImg;
    private ImageView alarmImg;

    private TextView temperatureValue;
    private TextView humidityValue;

    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    private NotificationCompat.Builder builder;
    private NotificationManagerCompat managerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sensors);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_farme);

        lightSensorAlarm = findViewById(R.id.lightSensorAlarm);
        doorSensor = findViewById(R.id.doreSensorState);
        flameSensorAlarm = findViewById(R.id.flameSensorAlarm);

        lightImg = findViewById(R.id.lightImg);
        flameImg = findViewById(R.id.flameImg);
        doorImg = findViewById(R.id.doorImg);
        alarmImg = findViewById(R.id.alarmImg);

        temperatureValue = findViewById(R.id.temperatureValue);
        humidityValue = findViewById(R.id.humidityValue);

        managerCompat = NotificationManagerCompat.from(Sensors.this);

        reference = FirebaseDatabase.getInstance().getReference("sensors");

        reference.child("light").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().equals("ON")) {
                    lightSensorAlarm.setChecked(true);
                } else {
                    lightSensorAlarm.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("flame/alarm").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().equals("ON")) {
                    flameSensorAlarm.setChecked(true);
                } else {
                    flameSensorAlarm.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("door").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue().equals("open")) {
                    doorSensor.setChecked(true);
                } else {
                    doorSensor.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temperatureValue.setText(snapshot.getValue().toString() + " °C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.child("humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                humidityValue.setText(snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lightSensorAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lightImg.setImageResource(R.drawable.light_on_icon);
                    lightSensorAlarm.setText("On");
                    reference.child("light").setValue("ON");
                } else {
                    lightImg.setImageResource(R.drawable.light_off_icon);
                    lightSensorAlarm.setText("Off");
                    reference.child("light").setValue("OFF");
                }
            }
        });
        flameSensorAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    flameImg.setImageResource(R.drawable.smoke_detector_on_icon);
                    flameSensorAlarm.setText("On");
                    reference.child("flame/alarm").setValue("ON");
                } else {
                    flameImg.setImageResource(R.drawable.smoke_detector_off_icon);
                    flameSensorAlarm.setText("Off");
                    reference.child("flame/alarm").setValue("OFF");
                }
            }
        });
        doorSensor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    doorImg.setImageResource(R.drawable.open_door_icon);
                    doorSensor.setText("Open");
                    reference.child("door").setValue("open");
                } else {
                    doorImg.setImageResource(R.drawable.close_door_icon);
                    doorSensor.setText("Close");
                    reference.child("door").setValue("close");
                }
            }
        });

    }

    private void notifyUserAboutDoorIsOpenAndAlarmIsOn() {
        builder = new NotificationCompat.Builder(Sensors.this, "My Notification")
                .setContentTitle("Warning")
                .setContentText("The door is open")
                .setSmallIcon(R.drawable.cow_icon)
                .setAutoCancel(true);
        managerCompat.notify(2, builder.build());
    }

    private void notifyUserAboutLightIsOnWhenAlarmIsOn() {
        builder = new NotificationCompat.Builder(Sensors.this, "My Notification")
                .setContentTitle("Warning")
                .setContentText("The light is turn On")
                .setSmallIcon(R.drawable.cow_icon)
                .setAutoCancel(true);
        managerCompat.notify(1, builder.build());
    }

    private void notifyUserAboutFireFlameIsOnAndCloseDoor() {
        builder = new NotificationCompat.Builder(Sensors.this, "My Notification")
                .setContentTitle("Warning")
                .setContentText("The fire is on in your farme we open the door to can animal run a way")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true);
        managerCompat.notify(3, builder.build());

        doorImg.setImageResource(R.drawable.open_door_icon);
        doorSensor.setText("Open");
        reference.child(firebaseUser.getUid()).child("door").setValue("open");
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_farme);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                startActivity(new Intent(Sensors.this, Home.class));
                break;
            case R.id.nav_map:
                startActivity(new Intent(Sensors.this, MapLocation.class));
                break;
            case R.id.nav_animals:
                startActivity(new Intent(Sensors.this, AllCategory.class));
                break;
            case R.id.nav_tank:
                startActivity(new Intent(Sensors.this, MapLocation.class));
                break;
            case R.id.nav_camera:
                break;
            case R.id.nav_weather:
                break;
            case R.id.nav_login:
                break;
            case R.id.nav_profile:
                break;
            case R.id.nav_logout:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_rate:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}