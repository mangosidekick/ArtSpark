package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MoodboardMenu extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    FloatingActionButton addButton;
    GridView gridView;
    MyCardAdapter adapter;
    List<CardItem> cardList;

    String MOODBOARD_KEY = "moodboards";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard_menu);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        gridView = findViewById(R.id.card_list);

        // should be loading moodboard from storage (Databases, or etc)
        cardList = loadMoodboards();

        // Checking
        Log.d("MoodboardMenu", "CARDLIST SIZE LOAD: " + cardList.size());
        Log.d("MoodboardMenu", "CARDLIST SIZE LOAD: " + cardList);

        adapter = new MyCardAdapter(this, cardList);  // Initialize the adapter
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Going from Moodboard menu to MoodboardEditing
                CardItem selectedCardItem = cardList.get(position);
                Intent intent = new Intent(MoodboardMenu.this, Moodboard1.class);
                intent.putExtra("moodboardId", selectedCardItem.getId());
                intent.putExtra("moodboardTitle", selectedCardItem.getTitle());

                Log.d("MoodboardMenu", "MOODBOARD_ID: " + selectedCardItem.getId());
                startActivity(intent);
            }
        });

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBoard();
            }
        });
    }

    private List<CardItem> loadMoodboards() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MOODBOARD_KEY, Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        List<CardItem> moodboards = new ArrayList<>();
        Gson gson = new Gson();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String json = (String) entry.getValue();
            CardItem moodboard = gson.fromJson(json, CardItem.class);
            moodboards.add(moodboard);
        }

        // Checking
        Log.d("MoodboardMenu", "MOODBOARD SIZE LOAD: " + moodboards.size());
        Log.d("MoodboardMenu", "MOODBOARD SIZE LOAD: " + moodboards);

        // Temporary when using SharedPreferences: order is not guaranteed.
        Collections.sort(moodboards, new Comparator<CardItem>() {
            @Override
            public int compare(CardItem cardItem, CardItem t1) {
                String titleT0 = cardItem.getTitle().split(" ")[1];
                String titleT1 = t1.getTitle().split(" ")[1];

                return Integer.parseInt(titleT0) - Integer.parseInt(titleT1);
            }
        });

        return moodboards;
    }

    private void addNewBoard() {
        String newBoardId = UUID.randomUUID().toString();
        int newBoardTitleCounter = cardList.size() + 1;

        CardItem newCardItem = new CardItem(newBoardId, "Moodboard " + newBoardTitleCounter);

        // Add the new moodboard to the list
        cardList.add(newCardItem);

        // Save the new moodboard
        saveMoodboard(newCardItem);

        // Checking
        Log.d("MoodboardMenu", "MOODBOARD SIZE: " + cardList.size());
        Log.d("MoodboardMenu", "MOODBOARD SIZE: " + cardList);

        // Refresh the GridView
        adapter.notifyDataSetChanged();
    }

    private void saveMoodboard(CardItem cardItem) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MOODBOARD_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(cardItem);
        editor.putString(cardItem.getId(), json);
        editor.apply();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            startActivity(new Intent(MoodboardMenu.this, GreetingScreen.class));
        }
    }
}
