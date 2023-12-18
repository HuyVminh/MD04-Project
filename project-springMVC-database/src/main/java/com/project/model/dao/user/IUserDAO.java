package com.project.model.dao.user;

import com.project.model.entity.Product;
import com.project.model.entity.User;

import java.util.List;

public interface IUserDAO {
    List<User> findAll();
    List<User> searchUser(String email);
    User findByEmail(String email);
    boolean register(User user);
    User login(String email,String password);
    void block(Integer id);
    boolean update(User user);
    User findById(int id);
}
