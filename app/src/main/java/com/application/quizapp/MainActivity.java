package com.application.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button startQuizButtonElement;
    private TextView nameInputTextElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get required references
        startQuizButtonElement = findViewById(R.id.startButton);
        nameInputTextElement = findViewById(R.id.nameInput);

        // Bind the button on click event
        startQuizButtonElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra(getString(R.string.nameIntentData), nameInputTextElement.getText().toString());
                startActivity(intent);
            }
        });
    }
}