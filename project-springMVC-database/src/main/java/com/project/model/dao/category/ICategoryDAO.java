package com.project.model.dao.category;

import com.project.model.entity.Category;

import java.util.List;

public interface ICategoryDAO {
    List<Category> findAll();
    Category findById(Integer id);
    List<Category> findByName(String name);
    boolean saveOrUpdate(Category category);
    void block(Integer id);

}
