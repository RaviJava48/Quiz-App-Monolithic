package com.ravin.quizapp.exception;

public class QuestionNotFoundException extends Exception {

    public QuestionNotFoundException() {
    }

    public QuestionNotFoundException(String message) {
        super(message);
    }
}
