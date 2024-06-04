package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SettingsScreen extends AppCompatActivity implements View.OnClickListener{
    ImageButton backButtonSettings;
    private TextView userText;
    private final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings_screen);
        getSupportActionBar();

        // set user as the saved username
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(USERNAME_KEY, "user");

        userText = findViewById(R.id.username);
        userText.setText(name);

        backButtonSettings = findViewById(R.id.backButton);
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
                } else if (itemId == R.id.tasks_menu) {
                    startActivity(new Intent(SettingsScreen.this, CalendarToDoList.class));
                    return true;
                } else return itemId == R.id.settings_menu;
            }
        });
    }
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            startActivity(new Intent(SettingsScreen.this, CalendarToDoList.class));
        }else if (v.getId() == R.id.changeTextButton){
            startActivity(new Intent(SettingsScreen.this, WelcomeScreen.class));
        }
    }
}