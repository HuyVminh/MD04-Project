package com.project.model.service.product;

import com.project.model.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(Integer id);
    List<Product> findByName(String name);
    boolean saveOrUpdate(Product product);
    void block(Integer id);
    boolean checkProductName(String productName);
    List<Product> getLastestProduct();
    List<Product> showAllWithPagination(int limit, int currenPage);
    List<Product> findByNameWithPagination(String name, int limit, int currenPage);
    Integer getTotalPagesOnShowAll(int limit, int currenPage);
    Integer getTotalPagesOnSearch(int limit, String name);
}
