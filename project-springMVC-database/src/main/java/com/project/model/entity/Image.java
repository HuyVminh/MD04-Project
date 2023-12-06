package com.project.model.entity;

public class Image {
    private int id;
    private int productId;
    private String imageName;

    public Image() {
    }

    public Image(int id, int productId, String imageName) {
        this.id = id;
        this.productId = productId;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
