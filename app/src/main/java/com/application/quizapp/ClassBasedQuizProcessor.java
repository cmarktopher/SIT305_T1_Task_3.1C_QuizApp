package com.application.quizapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class implementation will utilize class based data for each quiz question.
 */
public class ClassBasedQuizProcessor implements IQuizHandler {

    /**
     * Quiz class containing our quiz questions and answers
     */
    private class Quiz{

        private String Title;
        private String Question;
        private ArrayList<String> Answers;
        private Integer correctAnswerIndex;

        public Quiz(String newTitle, String newQuestion, ArrayList<String> newAnswers, Integer newCorrectAnswerIndex){

            Title = newTitle;
            Question = newQuestion;
            Answers = newAnswers;
            correctAnswerIndex = newCorrectAnswerIndex;
        }

        public String getTitle() {
            return Title;
        }

        public String getQuestion() {
            return Question;
        }

        public ArrayList<String> getAnswers() {
            return Answers;
        }

        public Integer getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }

    private ArrayList<Quiz> quizQuestions;
    private Integer currentQuestionIndex;

    public ClassBasedQuizProcessor(){

        // For now, I want to manually create questions
        quizQuestions = new ArrayList<>(Arrays.asList(

                new Quiz("Select One Option: ",
                        "Which one of these options are game engines?",
                        new ArrayList<>(Arrays.asList("SFML", "Monogame", "Unity", "SDL2")),
                        2
                ),
                new Quiz("Select One Option ",
                        "Does Blueprints belong to Unity or Unreal Engine",
                        new ArrayList<>(Arrays.asList("Unreal Engine", "Unity")),
                        0
                ),
                new Quiz("Select One Option ",
                        "What programming language does Unreal Engine use?",
                        new ArrayList<>(Arrays.asList("Java", "C#", "C++", "JavaScript", "Rust")),
                        2
                ),
                new Quiz("Select One Option ",
                        "What programming language does Unity use?",
                        new ArrayList<>(Arrays.asList("C++", "Rust", "Java", "Python", "C#")),
                        4
                )
        ));

        currentQuestionIndex = 0;
    }

    @Override
    public Integer GetCurrentQuestionIndex() {

        return currentQuestionIndex;
    }

    @Override
    public Integer GetMaxQuestionsCount() {

        return quizQuestions.size();
    }

    @Override
    public String GetQuestionTitle() {

        return quizQuestions.get(currentQuestionIndex).getTitle();
    }

    @Override
    public String GetQuestionText() {

        return quizQuestions.get(currentQuestionIndex).getQuestion();
    }

    @Override
    public ArrayList<String> GetQuestionAnswers() {

        return quizQuestions.get(currentQuestionIndex).getAnswers();
    }

    @Override
    public boolean CheckChosenAnswerCorrect(Integer answerIndex) {

        if (answerIndex == quizQuestions.get(currentQuestionIndex).correctAnswerIndex){
            return true;
        }

        return false;
    }

    @Override
    public boolean GoToNextQuestion() {

        currentQuestionIndex++;

        // This is my way of checking if we still have questions. If we have reached the end, we return false which the app will utilize to handle its own logic
        if (currentQuestionIndex < quizQuestions.size() ){

            return true;
        }

        return false;
    }
}
