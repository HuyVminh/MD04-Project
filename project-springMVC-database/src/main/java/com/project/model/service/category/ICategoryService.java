package com.project.model.service.category;

import com.project.model.entity.Category;

import java.util.ArrayList;
import java.util.List;

public interface ICategoryService {
    List<Category> findAll();
    Category findById(Integer id);
    List<Category> findByName(String name);
    boolean saveOrUpdate(Category category);
    void block(Integer id);
    boolean checkCategoryName(String categoryName);
    boolean checkProductByCategoryId(int id);
    List<Category> getCategoryList();
}
