package com.mango.artsparkxml;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

public class Moodboard1 extends AppCompatActivity implements View.OnClickListener {

    private android.widget.ImageButton ImageButton;
    ImageButton btnBack = (ImageButton);
    TextView moodboardTitle;

    CardItem cardItem;
    String moodboardId;
    String title;

    String MOODBOARD_KEY = "moodboards";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard1);

        // Instantiate
        btnBack = findViewById(R.id.backButton);
        moodboardTitle = findViewById(R.id.moodboardTitle);

        // Fetch data that is passed from Moodboard Menu
        Intent intent = getIntent();
        moodboardId = intent.getStringExtra("moodboardId");
        title = intent.getStringExtra("moodboardTitle");

        Log.d("Moodboard1", "Received MOODBOARD_ID: " + moodboardId);

        if (moodboardId != null) {
            moodboardTitle.setText(title);
            cardItem = loadMoodboardById(moodboardId);
        }

        // Initialize your canvas and other UI elements to display and edit the moodboard
        // For example:
        // CanvasView canvasView = findViewById(R.id.canvasView);
        // canvasView.setMoodboard(moodboard);

        // Set Click Listener
        btnBack.setOnClickListener(this);
    }

    private CardItem loadMoodboardById(String id) {
        SharedPreferences sharedPreferences = getSharedPreferences(MOODBOARD_KEY, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(id, null);
        return gson.fromJson(json, CardItem.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMoodboard(cardItem);
    }

    private void saveMoodboard(CardItem cardItem) {
        // Save the moodboard to storage
        // Example: SharedPreferences, Database, etc.
    }

    @Override
    public void onClick(View v) { // Ephraim added back button function
        if (v == btnBack) { // For the back button so it goes back to the last screen
            finish();
        }
    }
}