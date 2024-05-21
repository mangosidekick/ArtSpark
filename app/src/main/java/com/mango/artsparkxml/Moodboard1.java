package com.mango.artsparkxml;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Moodboard1 extends AppCompatActivity implements View.OnClickListener {

    private android.widget.ImageButton ImageButton;
    ImageButton btnBack = (ImageButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard1);

        // Instantiate
        btnBack = findViewById(R.id.backButton);

        // Set Click Listener
        btnBack.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) { // Ephraim added back button function
        if (v == btnBack) { // For the back button so it goes back to the last screen
            finish();
        }
    }
}