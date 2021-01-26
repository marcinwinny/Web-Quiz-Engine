package com.marcinwinny.engine.model;

public class AnswerResponse {

    public static final AnswerResponse CORRECT_ANSWER = new AnswerResponse(true, "Congratulations, you're right!");
    public static final AnswerResponse WRONG_ANSWER = new AnswerResponse(false, "Wrong answer! Please, try again.");

    private final boolean success;
    private final String feedback;

    private AnswerResponse(boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public boolean getSuccess() {
        return success;
    }

    public String getFeedback() {
        return feedback;
    }
}