package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GreetingScreen extends AppCompatActivity implements View.OnClickListener {

    private Button BtnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_greeting_screen);

        BtnEnter = findViewById(R.id.enterbutton);
        BtnEnter.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.enterbutton){
            startActivity(new Intent(GreetingScreen.this, MoodboardMenu.class));
        }
    }
}