package com.project.model.entity;

public class Category {
    private int categoryId;
    private String categoryName;
    private boolean status;
    private String description;

    public Category() {
    }

    public Category(int categoryId, String categoryName, boolean status, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
