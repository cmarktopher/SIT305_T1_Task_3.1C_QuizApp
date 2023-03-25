package com.application.quizapp;

import java.util.ArrayList;

public interface IQuizHandler {

    Integer GetCurrentQuestionIndex();
    Integer GetMaxQuestionsCount();
    String GetQuestionTitle();
    String GetQuestionText();
    ArrayList<String> GetQuestionAnswers();
    boolean CheckChosenAnswerCorrect(Integer answerIndex);
    boolean GoToNextQuestion();
}
