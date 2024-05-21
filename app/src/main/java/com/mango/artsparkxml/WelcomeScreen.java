package com.mango.artsparkxml;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class WelcomeScreen extends AppCompatActivity implements View.OnClickListener {

    private android.widget.EditText EditText;
    private android.database.sqlite.SQLiteDatabase SQLiteDatabase;
    private android.widget.Button Button;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    Button enterBtn = (Button);
    EditText userName = (EditText);
    SQLiteDatabase db = (SQLiteDatabase);
    String prevStarted = "yes";


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(prevStarted, Boolean.TRUE);
            editor.apply();
        } else {
            moveToSecondary();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_screen);

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        mEditor.putString("key", "mitch");
        mEditor.commit();

        userName = findViewById(R.id.entername);
        enterBtn = findViewById(R.id.enterbutton);
        enterBtn.setOnClickListener(this);
    }

    public void moveToSecondary(){
        // use an intent to travel from one activity to another.
        Intent intent = new Intent(this,GreetingScreen.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if( TextUtils.isEmpty(userName.getText()) && v.getId() == R.id.enterbutton ){
            Toast.makeText(this, "Name is required!", Toast.LENGTH_SHORT).show();
        }else{
            String name = userName.getText().toString();
            mEditor.putString(getString(R.string.username), name);
            mEditor.commit();
            Intent intent = new Intent(WelcomeScreen.this, MoodboardMenu.class);
            startActivity(intent);
        }


    }


}