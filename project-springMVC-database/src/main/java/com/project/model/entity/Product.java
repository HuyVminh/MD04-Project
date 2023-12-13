package com.project.model.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Product {
    private int productId;
    @NotEmpty(message = "Không được để trống !")
    private String productName;
    private String description;
@Min(value = 1,message = "Giá không thể nhỏ hơn 1 !")
    private float price;
    private Category category;
    private boolean status;
    @Min(value = 0,message = "Số lượng không thể nhỏ hơn 0 !")
    private int stock;
    @NotEmpty(message = "Hãy thêm ảnh cho sản phẩm !")
    private String imageUrl;

    public Product() {
    }

    public Product(int productId, String productName, String description, float price, Category category, boolean status, int stock, String imageUrl) {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.category = category;
        this.status = status;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
