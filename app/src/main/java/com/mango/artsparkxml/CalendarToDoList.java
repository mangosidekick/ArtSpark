package com.mango.artsparkxml;

import static com.mango.artsparkxml.R.id.bottomNavigationView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mango.artsparkxml.Adapter.ToDoAdapter;
import com.mango.artsparkxml.Model.ToDoModel;
import com.mango.artsparkxml.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CalendarToDoList extends AppCompatActivity implements DialogCloseListener, View.OnClickListener {
    ImageButton backButton;
    RelativeLayout tasksNotice;

    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;
    private FloatingActionButton addTasks;

    private List<ToDoModel> taskList;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar_todolist);
        getSupportActionBar();

        db = new DatabaseHandler(this);
        db.openDatabase();

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        tasksNotice = findViewById(R.id.tasks_notice);

        // Initialize the task list
        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(db, this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        addTasks = findViewById(R.id.addTasks);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(tasksAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);

        updateTasksNoticeVisibility();

        addTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.tasks_menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.home_menu) {
                    startActivity(new Intent(CalendarToDoList.this, GreetingScreen.class));
                    return true;
                } else if (itemId == R.id.board_menu) {
                    startActivity(new Intent(CalendarToDoList.this, MoodboardMenu.class));
                    return true;
                } else if (itemId == R.id.settings_menu) {
                    startActivity(new Intent(CalendarToDoList.this, SettingsScreen.class));
                    return true;
                } else return itemId == R.id.tasks_menu;
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.backButton) {
            startActivity(new Intent(CalendarToDoList.this, MoodboardMenu.class));
        }
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        taskList = db.getAllTasks();
        Collections.reverse(taskList);
        tasksAdapter.setTasks(taskList);
        tasksAdapter.notifyDataSetChanged();

        updateTasksNoticeVisibility();
    }

    private void updateTasksNoticeVisibility() {
        if (taskList.isEmpty()) {
            tasksNotice.setVisibility(View.VISIBLE);
        } else {
            tasksNotice.setVisibility(View.INVISIBLE);
        }
    }
}
