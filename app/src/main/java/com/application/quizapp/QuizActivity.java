package com.application.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * This activity will handle all quiz related logic.
 * Progression of the quiz will also be done here where I'll swap text out dynamically.
 * It didn't make much sense to me to have each question and answer in their own activity since the information is almost the same.
 * Also, I will make use of interfaces here so that this class doesn't have to care about how questions and stored or processed. It's only job is to handle the view.
 */
public class QuizActivity extends AppCompatActivity {

    // UI Elements
    private TextView welcomeTextElement;
    private TextView questionsTracker;
    private ProgressBar progressBar;
    private TextView questionTitle;
    private TextView questionText;
    private LinearLayout answerButtonLayout;
    private Button quizProgressionButton;

    private ArrayList<Button> answerButtons = new ArrayList<>();

    // Quiz Handler
    IQuizHandler quizHandler;

    // Answer tracking
    Integer answerIndexSelection;
    Integer correctAnswers;

    // State - we can have two states, one where we let the user select an answer and the other to show if the user got the correct answer
    private enum QuizState{ SELECTION, PRESENTATION}
    private QuizState currentQuizState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get required references
        welcomeTextElement = findViewById(R.id.welcomeText);
        questionsTracker = findViewById(R.id.questionsTracker);
        progressBar = findViewById(R.id.quiz_progress_bar);
        questionTitle = findViewById(R.id.questionTitle);
        questionText = findViewById(R.id.questionText);
        answerButtonLayout = findViewById(R.id.answerButtonLayout);
        quizProgressionButton = findViewById(R.id.progressButton);

        // Create our quiz handler
        quizHandler = new ClassBasedQuizProcessor();

        // Get the intent so that we can use data sent from previous activity
        Intent intent = getIntent();

        // Set the welcome message
        welcomeTextElement.setText("Welcome " + intent.getStringExtra(getString(R.string.nameIntentData)) + "!");

        // Set the initial question
        SetNewQuestion();

        // My way of checking that we have selected something
        answerIndexSelection = -1;

        // Keep track of the correct answers, this will get passed into a new activity
        correctAnswers = 0;

        // The first state we will be in is the selection state
        currentQuizState = QuizState.SELECTION;

        // Assign a callback to the quiz progression button (this button can be used to submit answers or move to the next question)
        quizProgressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (currentQuizState){

                    // If we are in the selection state, we want to simply move to the presentation state which is basically changing some colors of buttons and some small logic
                    case SELECTION:

                        if (answerIndexSelection >= 0){

                            // Handles answer logic

                            if (quizHandler.CheckChosenAnswerCorrect(answerIndexSelection)) {

                                answerButtons.get(answerIndexSelection).setBackgroundColor(getResources().getColor(R.color.correct_answer_color));
                                correctAnswers += 1;

                            } else {

                                answerButtons.get(answerIndexSelection).setBackgroundColor(getResources().getColor(R.color.incorrect_answer_color));
                            }

                            // Switch the text of the button
                            quizProgressionButton.setText("Next");

                            // Assign the new state
                            currentQuizState = QuizState.PRESENTATION;
                        }

                        break;

                    // If we are in this state, we need to go to a new question
                    case PRESENTATION:

                        if (quizHandler.GoToNextQuestion() == true){

                            // If true, then we have more questions and should set a new question
                            SetNewQuestion();
                            currentQuizState = QuizState.SELECTION;
                        }else{

                            // If false, we have no more questions and therefore can go to the third activity to display results
                        }

                        break;
                }


            }
        });
    }

    /**
     * Sets a new question.
     */
    private void SetNewQuestion(){

        // Handle the progress bar
        Integer currentQuestionNumber = quizHandler.GetCurrentQuestionIndex() + 1; // This returns the index, so we need to increment by 1 since it will start at 0
        Integer maxNumberOfQuestions = quizHandler.GetMaxQuestionsCount();

        questionsTracker.setText(currentQuestionNumber + "/" + maxNumberOfQuestions);
        progressBar.setMax(maxNumberOfQuestions);
        progressBar.setProgress(currentQuestionNumber);

        // Set the title
        questionTitle.setText(quizHandler.GetQuestionTitle());

        // Set the question
        questionText.setText(quizHandler.GetQuestionText());

        // Clear the buttons if any
        answerButtonLayout.removeAllViews();
        answerButtons.clear();

        // Assign new answer buttons - TODO Probably should recycle these button instances instead of creating new ones each time (use factory?)
        ArrayList<String> quizAnswers = quizHandler.GetQuestionAnswers();

        for (int i = 0; i < quizAnswers.size(); i++) {

            // New button
            Button answerButton = new Button(this);

            // Add it to view and cache
            answerButtonLayout.addView(answerButton);
            answerButtons.add(answerButton);

            // Set initial color
            answerButton.setBackgroundColor(getResources().getColor(R.color.light_grey));

            // Set answer text
            answerButton.setText(quizAnswers.get(i));

            // Give some margin
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 60);
            answerButton.setLayoutParams(layoutParams);

            // Assign an on click event here
            // Is it safe for me to assume that since the method defined here is contained within the on click listener, then I won't have to worry about unregistering the on click events since I am clearing the button references earlier?
            answerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // When clicked, we want to reset the color of all buttons as well
                    for (int j = 0; j < answerButtons.size(); j++) {

                        answerButtons.get(j).setBackgroundColor(getResources().getColor(R.color.light_grey));
                    }

                    // Change color to indicate it has been selected
                    answerButton.setBackgroundColor(getResources().getColor(R.color.dark_grey));

                    // Keep track of what was selected
                    answerIndexSelection = answerButtons.indexOf(view);

                }
            });
        }
    }

}