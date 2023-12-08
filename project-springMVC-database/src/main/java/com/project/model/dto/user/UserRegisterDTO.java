package com.project.model.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserRegisterDTO {
    @NotEmpty(message = "Không được để trống !")
    private String userName;

    private String fullName;
    @Email(message = "Phải chứa ký tự '@' !")
    private String email;
    @Size(min = 6,max = 20)
    private String password;
    private String avatar;
    private String address;
    @NotEmpty(message = "Không được để rỗng !")
    private String phone;
    private boolean status = true;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String userName, String fullName, String email, String password, String avatar, String address, String phone, boolean status) {
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
