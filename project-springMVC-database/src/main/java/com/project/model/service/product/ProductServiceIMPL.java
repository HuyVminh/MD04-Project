package com.project.model.service.product;

import com.project.model.dao.product.IProductDAO;
import com.project.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceIMPL implements IProductService {
    @Autowired
    IProductDAO productDAO;

    @Override
    public List<Product> findAll() {
        return productDAO.findAll();
    }

    @Override
    public Product findById(Integer id) {
        return productDAO.findById(id);
    }

    @Override
    public List<Product> findByName(String name) {
        return productDAO.findByName(name);
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        return productDAO.saveOrUpdate(product);
    }

    @Override
    public void block(Integer id) {
        productDAO.block(id);
    }

    @Override
    public boolean checkProductName(String productName) {
        return productDAO.checkProductName(productName);
    }

    @Override
    public List<Product> getLastestProduct() {
        return productDAO.getLastestProduct();
    }

    @Override
    public List<Product> showAllWithPagination(int limit, int currenPage) {
        return productDAO.showAllWithPagination(limit, currenPage);
    }

    @Override
    public List<Product> findByNameWithPagination(String name, int limit, int currenPage) {
        return productDAO.findByNameWithPagination(name, limit, currenPage);
    }

    @Override
    public Integer getTotalPagesOnShowAll(int limit, int currenPage) {
        return productDAO.getTotalPagesOnShowAll(limit, currenPage);
    }

    @Override
    public Integer getTotalPagesOnSearch(int limit, String name) {
        return getTotalPagesOnSearch(limit, name);
    }
}
