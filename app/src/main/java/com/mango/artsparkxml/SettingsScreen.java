package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsScreen extends AppCompatActivity implements View.OnClickListener{
    ImageButton backButtonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_screen);
        getSupportActionBar();

        backButtonSettings = findViewById(R.id.backButtonSettings);
        backButtonSettings.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.settings_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            //navbar
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home_menu) {
                    startActivity(new Intent(SettingsScreen.this, GreetingScreen.class));
                    return true;
                } else if (itemId == R.id.board_menu) {
                    startActivity(new Intent(SettingsScreen.this, MoodboardMenu.class));
                    return true;
                } else if (itemId == R.id.settings_menu) {
                    startActivity(new Intent(SettingsScreen.this, SettingsScreen.class));
                    return true;
                } else return itemId == R.id.tasks_menu;
            }
        });
    }
    public void onClick(View v) {
        if (v.getId() == R.id.backButtonSettings) {
            startActivity(new Intent(SettingsScreen.this, CalendarToDoList.class));
        }
    }
}