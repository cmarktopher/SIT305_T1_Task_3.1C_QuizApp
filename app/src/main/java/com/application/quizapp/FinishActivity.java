package com.application.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    // Ui Elements
    TextView finishScreenText;
    TextView scoreText;
    Button takeNewQuizButton;
    Button finishButton;

    // Name
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        // Set ui element references
        finishScreenText = findViewById(R.id.finishScreenText);
        scoreText = findViewById(R.id.score);
        takeNewQuizButton = findViewById(R.id.new_quiz_button);
        finishButton = findViewById(R.id.finish_button);

        // Get the intent so that we can use data sent from previous activity
        Intent intent = getIntent();

        // Set the relevant properties
        name = intent.getStringExtra(getString(R.string.nameIntentData));
        finishScreenText.setText("Congratulations " + name + "!");
        scoreText.setText(intent.getStringExtra(getString(R.string.scoreIntentData)));

        // Set the on click handler for the take quiz button to go to the quiz activity
        takeNewQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra(getString(R.string.nameIntentData), name);
                startActivity(intent);
                finish();
            }
        });

        // Quit the app if finish button is pressed
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Do mobile apps normally allow the app itself to have an option to close? I don't recall this ever being an option so not sure if doing this is good practice
                finish();
            }
        });

    }
}