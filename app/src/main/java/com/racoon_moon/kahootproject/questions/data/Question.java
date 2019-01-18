package com.racoon_moon.kahootproject.questions.data;

public class Question
{

    private String id;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String quizId;
    private boolean answer1True;
    private boolean answer2True;
    private boolean answer3True;
    private boolean answer4True;


    public Question() {

    }

    public Question(String id, String question, String answer1, String answer2, String answer3, String answer4, String quizId,
            boolean answer1True, boolean answer2True, boolean answer3True, boolean answer4True) {
        this.id = id;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.quizId = quizId;
        this.answer1True = answer1True;
        this.answer2True = answer2True;
        this.answer3True = answer3True;
        this.answer4True = answer4True;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer1() {
        return answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public String getQuizId() { return quizId; }

    public boolean getAnswer1True() { return answer1True; }

    public boolean getAnswer2True() { return answer2True; }

    public boolean getAnswer3True() { return answer3True; }

    public boolean getAnswer4True() { return answer4True; }

    public void setId(String id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void setQuizId(String quizId) { this.quizId = quizId; }

    public void setAnswer1True(boolean answer1True) { this.answer1True = answer1True; }

    public void setAnswer2True(boolean answer2True) { this.answer2True = answer2True; }

    public void setAnswer3True(boolean answer3True) { this.answer3True = answer3True; }

    public void setAnswer4True(boolean answer4True) { this.answer4True = answer4True; }
}