package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class GreetingScreen extends AppCompatActivity implements View.OnClickListener {

    private Button BtnEnter;
    private TextView userText;
    private TextView quote;

    private final String USERNAME_KEY = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_greeting_screen);

        Random random = new Random();
        int randomNumber = random.nextInt(16);

        // set user as the saved username
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(USERNAME_KEY, "user");

        userText = findViewById(R.id.username);
        userText.setText(name);

        quote = findViewById(R.id.quote);
        quote.setText();

        BtnEnter = findViewById(R.id.enterbutton);
        BtnEnter.setOnClickListener(this);


    }

    public void onClick(View v) {
        if (v.getId() == R.id.enterbutton){
            startActivity(new Intent(GreetingScreen.this, MoodboardMenu.class));
        }
    }
}