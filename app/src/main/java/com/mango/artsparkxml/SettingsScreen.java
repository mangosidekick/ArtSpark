package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class SettingsScreen extends AppCompatActivity implements View.OnClickListener {
    ImageButton backButtonSettings, changeTextButton;
    private android.widget.EditText EditText;
    EditText userName = (EditText);
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


        userName = findViewById(R.id.username);
        userName.setHint(name);

        backButtonSettings = findViewById(R.id.backButton);
        backButtonSettings.setOnClickListener(this);

        changeTextButton = findViewById(R.id.changeTextButton);
        changeTextButton.setOnClickListener(this);


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

        } else if (v.getId() == R.id.changeTextButton) {
            if( TextUtils.isEmpty(userName.getText())) {
                Toast.makeText(this, "Name has not been changed", Toast.LENGTH_SHORT).show();
            } else {
                String name = userName.getText().toString();

                // if username available, save it into shared preferences
                SharedPreferences sp = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putString(USERNAME_KEY, name);
                editor.apply();

                Toast.makeText(this, "Name has been changed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}