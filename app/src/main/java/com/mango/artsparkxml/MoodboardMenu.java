package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MoodboardMenu extends AppCompatActivity implements View.OnClickListener {

    CardView card_item;
    ImageButton backButton;
    FloatingActionButton addButton;
    DatabaseHandler db;
    GridView gridView;
    MyCardAdapter adapter;
    List<CardItem> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard_menu);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        gridView = findViewById(R.id.card_list);
        cardList = new ArrayList<>();
        adapter = new MyCardAdapter(this, cardList);  // Initialize the adapter
        gridView.setAdapter(adapter);

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewCard();
            }
        });
    }

    private void addNewCard() {
        cardList.add(new CardItem("New Card Text"));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            finish();
        }
    }
}
