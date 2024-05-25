package com.mango.artsparkxml;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CalendarTodolist extends AppCompatActivity implements View.OnClickListener {

    ImageButton artSparkBurgerIcon, artSparkMoodBoardIcon, artSparkCalendarIcon, artSparkYouIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar_todolist);

    }

    @Override
    public void onClick(View v) {

    }
}