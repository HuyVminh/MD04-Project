package com.project.model.dao.user;

import com.project.model.entity.Product;
import com.project.model.entity.User;

import java.util.List;

public interface IUserDAO {
    List<User> findAll();
    User findById(Integer id);
    List<User> findByName(String name);
    boolean saveOrUpdate(User user);
    void block(Integer id);
}