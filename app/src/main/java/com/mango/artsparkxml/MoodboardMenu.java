package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoodboardMenu extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard_menu);

        ImageButton backButton;
        FloatingActionButton addButton;

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            startActivity(new Intent(MoodboardMenu.this, GreetingScreen.class));
        }
    }
}

/*package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MoodboardMenu extends AppCompatActivity implements View.OnClickListener {

    CardView b1, b2, b3, b4, b5, b6, b7, b8, b9;
    ImageButton backButton;
    FloatingActionButton addButton;
    boolean b2Visible, b3Visible, b4Visible, b5Visible, b6Visible, b7Visible, b8Visible, b9Visible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard_menu);


        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        // number of mood board
        if (v.getId() == R.id.addButton) {
            if (!b2Visible) {
                b1.setVisibility(View.VISIBLE);
                b2Visible = true;
            } else if (!b3Visible){
                b2.setVisibility(View.VISIBLE);
                b3Visible = true;
            } else if (!b4Visible){
                b3.setVisibility(View.VISIBLE);
                b4Visible = true;
            } else if (!b5Visible){
                b4.setVisibility(View.VISIBLE);
                b5Visible = true;
            } else if (!b6Visible){
                b5.setVisibility(View.VISIBLE);
                b6Visible = true;
            } else if (!b7Visible){
                b6.setVisibility(View.VISIBLE);
                b7Visible = true;
            } else if (!b8Visible){
                b7.setVisibility(View.VISIBLE);
                b8Visible = true;
            } else if (!b9Visible){
                b8.setVisibility(View.VISIBLE);
                b9Visible = true;
            } else {
                b9.setVisibility(View.VISIBLE);
            }



        }
        // add more for individual mood board
        if (v.getId() == R.id.board1) {
            intent = new Intent(this, Moodboard1.class);
            startActivity(intent);
        } else if (v.getId() == R.id.board1) {
            intent = new Intent(this, Moodboard2.class);
            startActivity(intent);
        } else if (v.getId() == R.id.board1) {
            intent = new Intent(this, Moodboard3.class);
            startActivity(intent);
        }

        if (v.getId() == R.id.backButton) {
            startActivity(new Intent(MoodboardMenu.this, GreetingScreen.class));
        }
    }
}*/
