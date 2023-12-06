package com.project.model.service.category;

import com.project.model.entity.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(Integer id);
    Category findByName(String name);
    boolean saveOrUpdate(Category category);
    void block(Integer id);
}
