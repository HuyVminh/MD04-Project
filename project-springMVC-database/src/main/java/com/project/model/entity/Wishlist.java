package com.project.model.entity;

public class Wishlist {
    private int id;
    private Product product;
    private int userId;

    public Wishlist() {
    }

    public Wishlist(int id, Product product, int userId) {
        this.id = id;
        this.product = product;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
