package com.mango.artsparkxml;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.mango.artsparkxml.Model.CardItem;
import com.mango.artsparkxml.Utils.DatabaseHandler;

public class Moodboard1 extends AppCompatActivity implements View.OnClickListener {

    private android.widget.ImageButton ImageButton;
    ImageButton btnBack = (ImageButton);
    TextView moodboardTitle;
    private com.google.android.material.floatingactionbutton.FloatingActionButton FloatingActionButton;
    FloatingActionButton editButton = (FloatingActionButton);
    ImageView moodboardThumbnail;

    CardItem cardItem;
    String moodboardId;
    String title;

    private DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard1);

        // Instantiate
        btnBack = findViewById(R.id.backButton);
        moodboardTitle = findViewById(R.id.moodboardTitle);
        editButton = findViewById(R.id.artSparkMoodBoardIcon);
        moodboardThumbnail = findViewById(R.id.moodBoard);

        dbHandler = new DatabaseHandler(this);
        dbHandler.openDatabase();

        // Fetch data that is passed from Moodboard Menu
        Intent intent = getIntent();
        moodboardId = intent.getStringExtra("moodboardId");
        title = intent.getStringExtra("moodboardTitle");

        Log.d("Moodboard1", "Received MOODBOARD_ID: " + moodboardId);
        Log.d("Moodboard1", "Received MOODBOARD_TITLE: " + title);

        // Initialize your canvas and other UI elements to display and edit the moodboard
        // For example:
        // CanvasView canvasView = findViewById(R.id.canvasView);
        // canvasView.setMoodboard(moodboard);
        if (moodboardId != null) {
            cardItem = loadMoodboardById(moodboardId);
            moodboardTitle.setText(cardItem.getTitle());

            // set the image as the thumbnail
            byte[] thumbnail = cardItem.getThumbnail();
            if (thumbnail != null && thumbnail.length != 0) {
                Bitmap imageBitmap = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);
                moodboardThumbnail.setImageBitmap(imageBitmap);
            }

            Log.d("Moodboard1", "Loaded MOODBOARD_ID: " + cardItem.getId());
            Log.d("Moodboard1", "Loaded MOODBOARD_TITLE: " + cardItem.getTitle());
        }

        // Set Click Listener
        btnBack.setOnClickListener(this);

        // set the edit button to goes to editing moodboard activity with the intent list of all images?
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Moodboard1.this, EditingMoodboardActivity.class);
                intent.putExtra("moodboardId", moodboardId);
                intent.putExtra("moodboardTitle", title);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // refresh
        cardItem = loadMoodboardById(moodboardId);
        moodboardTitle.setText(cardItem.getTitle());

        // set the image as the thumbnail
        byte[] thumbnail = cardItem.getThumbnail();
        if (thumbnail != null && thumbnail.length != 0) {
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.length);
            moodboardThumbnail.setImageBitmap(imageBitmap);
        }
    }

    private CardItem loadMoodboardById(String id) {
        CardItem selectedCardItem = dbHandler.getMoodboardById(id);
        return selectedCardItem;
    }

    @Override
    public void onClick(View v) { // Ephraim added back button function
        if (v == btnBack) { // For the back button so it goes back to the last screen
            finish();
        }
    }
}