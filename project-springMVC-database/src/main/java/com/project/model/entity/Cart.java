package com.project.model.entity;

public class Cart {
    private int cardId;
    private int userId;
    private Product product;
    private int quantity;

    public Cart(int cardId, int userId, Product product, int quantity) {
        this.cardId = cardId;
        this.userId = userId;
        this.product = product;
        this.quantity = quantity;
    }

    public Cart() {
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
