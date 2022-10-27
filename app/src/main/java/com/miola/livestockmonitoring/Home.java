package com.miola.livestockmonitoring;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.miola.livestockmonitoring.helpers.CurrentUser;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;
    private TextView textView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        // hooks

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.nameOfUserInDashboard);

        mAuth = FirebaseAuth.getInstance();

        // tool bar

        setSupportActionBar(toolbar);

        // navigation drawer menu

        // hide or show items

        menu = navigationView.getMenu();
        menu.findItem(R.id.nav_login).setVisible(false);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.nav_home:     break;
            case R.id.nav_map:     break;
            case R.id.nav_animals:     break;
            case R.id.nav_tank:     break;
            case R.id.nav_camera:     break;
            case R.id.nav_weather:     break;
            case R.id.nav_login:     break;
            case R.id.nav_profile:  startActivity(new Intent(Home.this, UserProfile.class));
                                    break;
            case R.id.nav_logout:   mAuth.signOut();
                System.out.println("user null ggggggggggggggggggggggggggggggggggggggggggggggggggggg");
                                    CurrentUser.setCurrentUser(null);
                                    menu.findItem(R.id.nav_logout).setVisible(false);
                                    menu.findItem(R.id.nav_profile).setVisible(false);
                                    menu.findItem(R.id.nav_login).setVisible(true);
                                    startActivity(new Intent(Home.this, SignIn.class));
                                    break;
            case R.id.nav_share: Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show(); break;
            case R.id.nav_rate:     break;
        }
        drawerLayout.closeDrawer(GravityCompat.START); return true;
    }

    public void choseAction(View view)
    {
        switch (Integer.parseInt(view.getTag().toString()))
        {
            case 10 : startActivity(new Intent(this, MapLocation.class));break;
            case 20 : startActivity(new Intent(this, AllCategory.class));break;
            case 30 : startActivity(new Intent(this, Sensors.class));break; // farme activity
            case 40 : startActivity(new Intent(this, AllCategory.class));break; // truk activity
            case 50 : startActivity(new Intent(this, AllCategory.class));break; // camera activity
            case 60 : startActivity(new Intent(this, AllCategory.class));break; // weather activity
        }
    }
}