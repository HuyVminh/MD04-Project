package com.project.model.dto.user;

public class UserCheckoutDTO {
    private String email;
    private String address;
    private String phone;
    private String fullName;
    private String note;

    public UserCheckoutDTO() {
    }

    public UserCheckoutDTO(String email, String address, String phone, String fullName, String note) {
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.fullName = fullName;
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
