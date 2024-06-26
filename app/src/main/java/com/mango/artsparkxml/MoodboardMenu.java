package com.mango.artsparkxml;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mango.artsparkxml.Model.CardItem;
import com.mango.artsparkxml.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MoodboardMenu extends AppCompatActivity implements View.OnClickListener {

    ImageButton backButton;
    FloatingActionButton addButton;
    GridView gridView;
    MyCardAdapter adapter;
    List<CardItem> cardList;
    RelativeLayout moodboardNotice;

    private DatabaseHandler dbHandler;

    int MOODBOARD_LIMIT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_moodboard_menu);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.board_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            //navbar
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home_menu) {
                    startActivity(new Intent(MoodboardMenu.this, GreetingScreen.class));
                    return true;
                } else if (itemId == R.id.board_menu) {
                    return true;
                } else if (itemId == R.id.tasks_menu) {
                    startActivity(new Intent(MoodboardMenu.this, CalendarToDoList.class));
                    return true;
                } else if (itemId == R.id.settings_menu) {
                    startActivity(new Intent(MoodboardMenu.this, SettingsScreen.class));
                    return true;
                }
                return false;
            }
        });

        dbHandler = new DatabaseHandler(this);
        dbHandler.openDatabase();

        moodboardNotice = findViewById(R.id.moodboard_notice);

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

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CardItem selectedMoodboard = cardList.get(position);
                showDeleteConfirmationDialog(selectedMoodboard, position);
                return true;
            }
        });

        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewBoard();
            }
        });

        // Update visibility based on initial data load
        updateMoodboardNoticeVisibility();
    }

    private List<CardItem> loadMoodboards() {
        List<CardItem> moodboards = dbHandler.getAllMoodboards();

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
        // check limit
        if ((cardList.size() + 1) > MOODBOARD_LIMIT) {
            Toast.makeText(this, "Moodboard limit is 100!", Toast.LENGTH_SHORT).show();
        } else {
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
            refreshGridView();
            updateMoodboardNoticeVisibility();
        }
    }

    private void saveMoodboard(CardItem cardItem) {
        dbHandler.insertMoodboard(cardItem.getId(), cardItem.getTitle(), new byte[0]);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            startActivity(new Intent(MoodboardMenu.this, GreetingScreen.class));
        }
    }

    private void showDeleteConfirmationDialog(final CardItem moodboard, final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Moodboard")
                .setMessage("Are you sure you want to delete this moodboard?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMoodboard(moodboard, position);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteMoodboard(CardItem moodboard, int position) {
        // Remove the moodboard from the list
        cardList.remove(position);

        // Remove the moodboard from SharedPreferences
        dbHandler.deleteMoodboard(moodboard.getId());

        // Notify the adapter about the removal
        adapter.notifyDataSetChanged();
        updateMoodboardNoticeVisibility();
    }

    private void updateMoodboardNoticeVisibility() {
        if (cardList.isEmpty()) {
            moodboardNotice.setVisibility(View.VISIBLE);
        } else {
            moodboardNotice.setVisibility(View.INVISIBLE);
        }
    }

    private void refreshGridView(){
        adapter.notifyDataSetChanged();
        gridView.invalidateViews();
        gridView.setAdapter(adapter);
    }
}
