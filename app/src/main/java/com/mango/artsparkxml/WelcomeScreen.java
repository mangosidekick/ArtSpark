package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {

    private android.widget.Button Button;
    Button enterBtn = (Button);
    private android.widget.EditText EditText;
    EditText userName = (EditText);

    private final String PREV_STARTED_KEY = "prevStarted";
    private final String USERNAME_KEY = "username";

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        if (!sharedpreferences.getBoolean(PREV_STARTED_KEY, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(PREV_STARTED_KEY, true);
            editor.apply();
        } else {
            moveToSecondary();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);

        userName = findViewById(R.id.entername);

        enterBtn = findViewById(R.id.enterbutton);
        enterBtn.setOnClickListener(this);
    }

    public void moveToSecondary(){
        // use an intent to travel from one activity to another.
        Intent intent = new Intent(this,GreetingScreen.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.enterbutton) {
            if( TextUtils.isEmpty(userName.getText())) {
                Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show();
            } else {
                String name = userName.getText().toString();

                // if username available, save it into shared preferences
                SharedPreferences sp = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();

                editor.putString(USERNAME_KEY, name);
                editor.apply();

                // and go to moodboard menu
                startActivity(new Intent(WelcomeScreen.this, MoodboardMenu.class));
            }
        } else {
            startActivity(new Intent(WelcomeScreen.this, MoodboardMenu.class));
            Intent i = new Intent(getApplicationContext(), WelcomeScreen.class);
            startActivity(i);
        }

    }
}