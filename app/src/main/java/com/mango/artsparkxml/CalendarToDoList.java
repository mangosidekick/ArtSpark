package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mango.artsparkxml.Adapter.ToDoAdapter;
import com.mango.artsparkxml.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

public class CalendarToDoList extends AppCompatActivity implements View.OnClickListener {
    ImageButton backButton;
    private RecyclerView tasksRecyclerView;
    private ToDoAdapter tasksAdapter;

    private List<ToDoModel> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar_todolist);
        getSupportActionBar();

        backButton = findViewById(R.id.backButtonCalendar);
        backButton.setOnClickListener(this);

        //the lists chuchu

        taskList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tasksAdapter = new ToDoAdapter(this);
        tasksRecyclerView.setAdapter(tasksAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("this is a test task");
        task.setStatus(0);
        task.setId(1);

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        tasksAdapter.setTasks(taskList);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.backButtonCalendar) {
            startActivity(new Intent(CalendarToDoList.this, MoodboardMenu.class));
        }
    }
}
