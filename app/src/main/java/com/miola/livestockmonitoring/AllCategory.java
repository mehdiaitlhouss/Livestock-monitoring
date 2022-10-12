package com.miola.livestockmonitoring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AllCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);
    }

    public void getAnimals(View view) {
        switch (Integer.parseInt(view.getTag().toString())) {
            case 10 : startActivity(new Intent(this, ListAnimals.class)); break;
            case 20 : startActivity(new Intent(this, ListAnimals.class)); break;
            case 30 : startActivity(new Intent(this, ListAnimals.class)); break;
        }
    }
}