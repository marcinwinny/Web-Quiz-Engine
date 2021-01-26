package com.marcinwinny.engine.model;

public class Answer {
    //    public List<Integer> answerArr;
    public int[] answer;

    public Answer() {
    }

    public Answer(int[] answerArr) {
        this.answer = answerArr;
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }

    public int[] getAnswerArr() {
        return answer;
    }
}