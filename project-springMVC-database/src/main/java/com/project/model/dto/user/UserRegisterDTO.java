package com.project.model.dto.user;

import javax.validation.constraints.*;

public class UserRegisterDTO {
    @NotEmpty(message = "Không được để trống !")
    private String userName;
    @Email(message = "Phải chứa ký tự '@' !")
    private String email;
    @Size(min = 6,max = 20,message = "Phải chứa từ 6 đến 20 ký tự !")
    private String password;
    @Size(min = 6,max = 20,message = "Phải chứa từ 6 đến 20 ký tự !")
    private String confirmPassword;

    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b",message = "Không đúng định dạng số điện thoại !")
    private String phone;

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String userName, String email, String password, String confirmPassword, String phone) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
    }
    @AssertTrue(message = "Mật khẩu không khớp")
    public boolean isPasswordMatch() {
        return password.equals(confirmPassword);
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
