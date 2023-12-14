package com.project.model.service.category;

import com.project.model.dao.category.ICategoryDAO;
import com.project.model.dao.product.IProductDAO;
import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceIMPL implements ICategoryService {
    @Autowired
    private ICategoryDAO categoryDAO;
    @Autowired
    private IProductService productService;

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return categoryDAO.findById(id);
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryDAO.findByName(name);
    }

    @Override
    public boolean saveOrUpdate(Category category) {
        return categoryDAO.saveOrUpdate(category);
    }

    @Override
    public void block(Integer id) {
        categoryDAO.block(id);
    }

    @Override
    public boolean checkCategoryName(String categoryName) {
        return categoryDAO.checkCategoryName(categoryName);
    }

    @Override
    public boolean checkProductByCategoryId(int id) {
        List<Product> products = productService.findAll();
        for (Product product : products) {
            if (product.getCategory().getCategoryId() == id) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Category> getCategoryList() {
        List<Category> categoryList = findAll();
        List<Category> categories = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.isStatus()){
                categories.add(category);
            }
        }
        return categoryList;
    }

}
