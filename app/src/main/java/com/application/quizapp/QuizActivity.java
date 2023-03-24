package com.application.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    private TextView welcomeTextElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get required references
        welcomeTextElement = findViewById(R.id.welcomeText);

        // Get the intent so that we can use data sent from previous activity
        Intent intent = getIntent();

        // Set the welcome message
        welcomeTextElement.setText("Welcome " + intent.getStringExtra(getString(R.string.nameIntentData)) + "!");
    }
}