package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mango.artsparkxml.Utils.DatabaseHandler;

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

        int[] strs = new int[] {R.string.quote1, R.string.quote2, R.string.quote3, R.string.quote4, R.string.quote5, R.string.quote6, R.string.quote7, R.string.quote8, R.string.quote9, R.string.quote10, R.string.quote11, R.string.quote12, R.string.quote13, R.string.quote14, R.string.quote15};
        int randomIndex = new Random().nextInt(15);
        int resId = strs[randomIndex];
        String randomString = getString(resId);

        // set user as the saved username
        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        String name = sharedpreferences.getString(USERNAME_KEY, "user");

        userText = findViewById(R.id.username);
        userText.setText(name);

        quote = findViewById(R.id.quote);
        quote.setText(randomString);

        BtnEnter = findViewById(R.id.enterbutton);
        BtnEnter.setOnClickListener(this);

        //display icon
        ImageView navImage = (ImageView) findViewById(R.id.profile_image);
        DatabaseHandler dbHelper = new DatabaseHandler(this);
        Cursor cursor = dbHelper.getUser();

        if (cursor.getCount() == 0){
            Toast.makeText(this, "No image", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                byte[] imageByte = cursor.getBlob(0);

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                navImage.setImageBitmap(bitmap);
            }
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.enterbutton){
            startActivity(new Intent(GreetingScreen.this, MoodboardMenu.class));
        }
    }
}