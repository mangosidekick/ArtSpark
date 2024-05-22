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

    ImageButton artSparkBurgerIcon, artSparkMoodBoardIcon, artSparkCalendarIcon, artSparkYouIcon;
    CardView card_item;
    ImageButton backButton;
    FloatingActionButton addButton;
    DatabaseHandler db;
    GridView gridView;
    MyCardAdapter adapter;
    List<CardItem> cardList;
    int idCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard_menu);

        // very crude. should count the actual size of saved moodboards.
        idCounter = 1;

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        artSparkBurgerIcon = findViewById(R.id.artSparkBurgerIcon);
        artSparkBurgerIcon.setOnClickListener(this);

        artSparkMoodBoardIcon = findViewById(R.id.artSparkMoodBoardIcon);
        artSparkMoodBoardIcon.setOnClickListener(this);

        artSparkCalendarIcon = findViewById(R.id.artSparkCalendarIcon);
        artSparkCalendarIcon.setOnClickListener(this);

        artSparkYouIcon = findViewById(R.id.artSparkYouIcon);
        artSparkYouIcon.setOnClickListener(this);

        gridView = findViewById(R.id.card_list);

        // should be loading moodboard from storage (Databases, or etc)
        cardList = loadMoodboards();

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
        cardList.add(new CardItem("MOODBOARD_" + idCounter, "Moodboard " + idCounter));
        idCounter++;
        adapter.notifyDataSetChanged();
    }

    private List<CardItem> loadMoodboards() {
        return new ArrayList<>(); // Replace with actual data loading
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            finish();
        }else if (v.getId() == R.id.artSparkBurgerIcon){
            finish();
        }else if (v.getId() == R.id.artSparkMoodBoardIcon){
            startActivity(new Intent(MoodboardMenu.this, MoodboardMenu.class));
        }else if (v.getId() == R.id.artSparkCalendarIcon){
            startActivity(new Intent(MoodboardMenu.this, CalendarTodolist.class));
        }else if (v.getId() == R.id.artSparkYouIcon){
            finish();
        }
    }
}
