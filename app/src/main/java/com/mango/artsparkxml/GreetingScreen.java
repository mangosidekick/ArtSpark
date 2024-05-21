package com.mango.artsparkxml;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GreetingScreen extends AppCompatActivity implements View.OnClickListener {
    private android.widget.EditText EditText;
    EditText userName = (EditText);
    private android.widget.Button Button;
    Button enterBtn = (Button);
    private android.widget.TextView TextView;
    TextView txt = (TextView);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_greeting_screen);

       enterBtn = findViewById(R.id.enterbutton);
       enterBtn.setOnClickListener(this);
        txt = findViewById(R.id.username);
        txt.setText(userName.getText().toString());
    }

    public void onClick(View v) {
        if (v.getId() == R.id.enterbutton){
            startActivity(new Intent(GreetingScreen.this, MoodboardMenu.class));
        }
    }
}