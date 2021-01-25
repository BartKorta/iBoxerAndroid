package com.example.iboxer;

import java.time.ZonedDateTime;

public class Scores {
    private String userId;
    private int score;
    private String date;

    public Scores(String userId, int score, String date) {
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
