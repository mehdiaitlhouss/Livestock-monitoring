package com.miola.livestockmonitoring;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.miola.livestockmonitoring.DRVInterface.LoadMore;
import com.miola.livestockmonitoring.helpers.CurrentUser;
import com.miola.livestockmonitoring.helpers.SessionManager;
import com.miola.livestockmonitoring.model.Animal;
import com.miola.livestockmonitoring.model.DynamicRVAdapter;
import com.miola.livestockmonitoring.model.DynamicRVModel;
import com.miola.livestockmonitoring.model.StaticRvAdapter;
import com.miola.livestockmonitoring.model.StaticRvModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListAnimals extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;

    private RecyclerView recyclerView;
    private StaticRvAdapter staticRvAdapter;
    private DynamicRVAdapter dynamicRVAdapter;
    private TextView typeAnimalName;

    private List<DynamicRVModel> items = new ArrayList<>();
    ArrayList<StaticRvModel> item = new ArrayList<>();
    private SessionManager sessionManager;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_list_animals);

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

        typeAnimalName = findViewById(R.id.typeAnimalName);

        sessionManager = new SessionManager(this);

        getListAnimals(sessionManager.getAnimalTypeFromSession());

        recyclerView = findViewById(R.id.rv_1);
        staticRvAdapter = new StaticRvAdapter(item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(staticRvAdapter);

        RecyclerView drv = findViewById(R.id.rv_2);
        drv.setLayoutManager(new LinearLayoutManager(this));
        dynamicRVAdapter = new DynamicRVAdapter(drv, this, items);
        drv.setAdapter(dynamicRVAdapter);
        dynamicRVAdapter.setLoadMore(new LoadMore() {
            @Override
            public void onLoadMore() {

                if (items.size() < -10) {
                    item.add(null);
                    dynamicRVAdapter.notifyItemInserted(items.size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            items.remove(items.size() - 1);
                            dynamicRVAdapter.notifyItemRemoved(items.size());
                            int index = items.size();
                            int end = index + 10;

                            for (int i = index; i < end; i++) {
                                String name = UUID.randomUUID().toString();
                                DynamicRVModel item = new DynamicRVModel(name, R.drawable.farmer_icon);
                                items.add(item);
                            }
                            dynamicRVAdapter.notifyDataSetChanged();
                            dynamicRVAdapter.setLoaded();
                        }
                    }, 4000);
                }
                else {
                    Toast.makeText(ListAnimals.this, "Data Completed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getListAnimals(String s) {
        ArrayList<Animal> animals;
        int img;

        if (s.equals("cow")) {
            animals = new ArrayList<>(CurrentUser.getCurrentUser().getCows().values());
            img = R.drawable.cow_icon;
            typeAnimalName.setText(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        else if (s.equals("sheep")) {
            animals = new ArrayList<>(CurrentUser.getCurrentUser().getCows().values());
            img = R.drawable.sheep_icon;
            typeAnimalName.setText(s.substring(0, 1).toUpperCase() + s.substring(1));
        }
        else {
            animals = new ArrayList<>(CurrentUser.getCurrentUser().getCows().values());
            img = R.drawable.chicken_icon;
            typeAnimalName.setText(s.substring(0, 1).toUpperCase() + s.substring(1));
        }

        item.add(new StaticRvModel(img, "Male", R.drawable.static_rv_selector_bg_male));
        item.add(new StaticRvModel(img, "Famel ", R.drawable.static_rv_selector_bg_famel));
        item.add(new StaticRvModel(img, "Child", R.drawable.static_rv_selector_bg_child));

        for (Animal animal : animals) {
            items.add(new DynamicRVModel(animal.getName(), img));
        }
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
            case R.id.nav_home:
                break;
            case R.id.nav_map:
                break;
            case R.id.nav_animals:
                break;
            case R.id.nav_tank:
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