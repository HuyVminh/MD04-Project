package com.project.model.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserLoginDTO {
    @Email(message = "Phải chứa ký tự '@' !")
    private String email;
    @Size(min = 6,max = 20)
    private String password;
    private boolean status;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password, boolean status) {
        this.email = email;
        this.password = password;
        this.status = status;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
