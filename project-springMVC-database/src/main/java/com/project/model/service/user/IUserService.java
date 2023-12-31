package com.project.model.service.user;

import com.project.model.dto.user.UserRegisterDTO;
import com.project.model.entity.Product;
import com.project.model.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    List<User> searchUser(String email);
    User findByEmail(String email);
    boolean register(UserRegisterDTO user);
    User login(String email,String password);
    void block(Integer id);
    boolean update(User user);
}
