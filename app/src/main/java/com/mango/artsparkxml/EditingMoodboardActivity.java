package com.mango.artsparkxml;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mango.artsparkxml.Model.ImageModel;
import com.mango.artsparkxml.Utils.DatabaseHandler;

import java.io.IOException;
import java.util.List;

public class EditingMoodboardActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private String moodboardId;
    private String title;
    private DatabaseHandler dbHandler;

    FloatingActionButton uploadButton;
    ImageButton btnBack;
    ImageButton btnSave;
    TextView moodboardTitle;
    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_moodboard);

        btnBack = findViewById(R.id.backButton);
        btnSave = findViewById(R.id.saveButton);
        moodboardTitle = findViewById(R.id.moodboardTitle);
        canvasView = findViewById(R.id.canvasView);

        dbHandler = new DatabaseHandler(this);
        dbHandler.openDatabase();

        Intent intent = getIntent();
        moodboardId = intent.getStringExtra("moodboardId");
        title = intent.getStringExtra("moodboardTitle");

        if (moodboardId != null) {
            loadMoodboard();

            moodboardTitle.setText(title);

            // set the image as the thumbnail

        }

        uploadButton = findViewById(R.id.addElementButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    openImagePicker();
                } else {
                    requestPermission();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMoodboard();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMoodboard();
        Log.d("onPause:", "saving");
    }

    private boolean checkPermission() {
        int currentApiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentApiVersion >= android.os.Build.VERSION_CODES.M) {
            // Runtime permission required for API 23 and above
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            // No runtime permission required for API below 23
            return true;
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                PERMISSION_REQUEST_CODE
        );
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImagePicker();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                canvasView.addImage(bitmap, imageUri.toString(), 0,0,1,1);
                saveMoodboard();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // different from the teacher

    private void saveMoodboard() {
        dbHandler.deleteImagesForMoodboard(moodboardId); // Clear existing entries
        for (ImageModel imageData : canvasView.getImages()) {
            dbHandler.insertImage(moodboardId, imageData);
        }
    }

    // Don't know why this is not working hhh
    private void updateMoodboard() {
        List<ImageModel> images = canvasView.getImages();
        for (ImageModel imageData : images) {
            dbHandler.updateImage(imageData.getId(), imageData.getX(), imageData.getY(), imageData.getScaleX(), imageData.getScaleY());
        }
    }

    private void loadMoodboard() {
        List<ImageModel> images = dbHandler.getImagesForMoodboard(moodboardId);
        for (ImageModel imageData : images) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imageData.getUri()));
                imageData.setBitmap(bitmap);
                canvasView.addImage(bitmap, imageData.getUri(), imageData.getX(),imageData.getY(),imageData.getScaleX(),imageData.getScaleY());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
