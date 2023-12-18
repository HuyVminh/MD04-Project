package com.project.model.entity;

import java.sql.Date;

public class Order {
    private int orderId;
    private User user;
    private String orderCustomerName;
    private String address;
    private String phoneNumber;
    private String note;
    private Date created_date;
    private int status;
    private float total;

    public Order() {
    }

    public Order(int orderId, User user, String orderCustomerName, String address, String phoneNumber, String note, Date created_date, int status, float total) {
        this.orderId = orderId;
        this.user = user;
        this.orderCustomerName = orderCustomerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.note = note;
        this.created_date = created_date;
        this.status = status;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderCustomerName() {
        return orderCustomerName;
    }

    public void setOrderCustomerName(String orderCustomerName) {
        this.orderCustomerName = orderCustomerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
