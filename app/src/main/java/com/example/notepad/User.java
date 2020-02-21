package com.example.notepad;

public class User {
    private String title;
    private String details;
    private String userId;

    public User() {
    }

    public User(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public User(String title, String details, String userId) {
        this.title = title;
        this.details = details;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
