package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeScreen extends AppCompatActivity {
    private Button enterBtn;
    private EditText enterName;

    private final String SHARED_PREF_NAME = getString(R.string.app_name);
    private final String FIRST_START_KEY = "firstStart";
    private final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        SharedPreferences sharedpreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // if this is the first time running, run the enter username screen
        if (sharedpreferences.getBoolean(FIRST_START_KEY, true)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(FIRST_START_KEY, false);
            editor.commit();

            // add setup welcome screen
            setContentView(R.layout.activity_welcome_screen);

            enterName = findViewById(R.id.entername);

            enterBtn = findViewById(R.id.enterbutton);
            enterBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveName();
                    }
                }
            );
        } else {
            moveToSecondary();
        }
    }

    public void moveToSecondary(){
        // use an intent to travel from one activity to another.
        Intent intent = new Intent(this,GreetingScreen.class);
        startActivity(intent);
    }

    private void saveName() {
        String name = enterName.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(WelcomeScreen.this, "Name is required!", Toast.LENGTH_SHORT).show();
            enterName.setError( "First name is required!" );
            return;
        }

        // if username available, save it into shared preferences
        SharedPreferences sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(USERNAME_KEY, name);
        editor.apply();
    }

}