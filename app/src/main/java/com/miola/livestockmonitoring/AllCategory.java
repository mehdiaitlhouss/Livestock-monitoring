package com.miola.livestockmonitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;
import com.miola.livestockmonitoring.helpers.SessionManager;

public class AllCategory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_all_category);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_animals);
    }

    public void getAnimals(View view) {
        SessionManager sessionManager = new SessionManager(this);

        switch (Integer.parseInt(view.getTag().toString())) {
            case 10 :
                sessionManager.createAnimalTypeSession("sheep");
                break;
            case 20 :
                sessionManager.createAnimalTypeSession("cow");
                break;
            case 30 :
                sessionManager.createAnimalTypeSession("chicken");
                break;
        }

        startActivity(new Intent(this, ListAnimals.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.setCheckedItem(R.id.nav_animals);
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
            case R.id.nav_dashboard:
                break;
            case R.id.nav_home:
                startActivity(new Intent(AllCategory.this, Home.class));
                break;
            case R.id.nav_map:
                startActivity(new Intent(AllCategory.this, MapLocation.class));
                break;
            case R.id.nav_animals:
                startActivity(new Intent(AllCategory.this, AllCategory.class));
                break;
            case R.id.nav_tank:
                break;
            case R.id.nav_camera:
                break;
            case R.id.nav_profile:
                startActivity(new Intent(AllCategory.this, UserProfile.class));
                break;
            case R.id.nav_logout:
                startActivity(new Intent(AllCategory.this, SignIn.class));
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