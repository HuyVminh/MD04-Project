package com.project.model.dao.product;

import com.project.model.entity.Product;

import java.util.List;

public interface IProductDAO {
    List<Product> findAll();
    Product findById(Integer id);
    List<Product> findByName(String name);
    boolean saveOrUpdate(Product product);
    void block(Integer id);
    boolean checkProductName(String productName);
    List<Product> getLastestProduct();
    void updateStock(int productId, int quantity);
}
