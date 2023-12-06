package com.project.model.entity;

public class Comment {
    private int userId;
    private String comment;

    public Comment() {
    }

    public Comment(int userId, String comment) {
        this.userId = userId;
        this.comment = comment;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
