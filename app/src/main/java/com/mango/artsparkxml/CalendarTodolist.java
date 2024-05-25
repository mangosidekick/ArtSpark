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

        artSparkBurgerIcon = findViewById(R.id.artSparkBurgerIcon);
        artSparkBurgerIcon.setOnClickListener(this);

        artSparkMoodBoardIcon = findViewById(R.id.artSparkMoodBoardIcon);
        artSparkMoodBoardIcon.setOnClickListener(this);

        artSparkCalendarIcon = findViewById(R.id.artSparkCalendarIcon);
        artSparkCalendarIcon.setOnClickListener(this);

        artSparkYouIcon = findViewById(R.id.artSparkYouIcon);
        artSparkYouIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.artSparkBurgerIcon){
            finish();
        }else if (v.getId() == R.id.artSparkMoodBoardIcon){
            startActivity(new Intent(CalendarTodolist.this, MoodboardMenu.class));
        }else if (v.getId() == R.id.artSparkCalendarIcon){
            startActivity(new Intent(CalendarTodolist.this, CalendarTodolist.class));
        }else if (v.getId() == R.id.artSparkYouIcon){
            finish();
        }
    }
}