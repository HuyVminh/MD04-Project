package com.project.model.dao.product;

import com.project.model.dao.category.ICategoryDAO;
import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoIMPL implements IProductDAO {
    @Autowired
    ICategoryDAO categoryDAO;
    @Override
    public List<Product> findAll() {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_PRODUCT}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Product findById(Integer id) {
        Connection connection = null;
        Product product = new Product();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_PRODUCT_BY_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> findByName(String name) {
        Connection connection = null;
        List<Product> foundList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_PRODUCT_BY_NAME(?)}");
            String nameSearch = name.toLowerCase().trim();
            callableStatement.setString(1, nameSearch);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
                foundList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return foundList;
    }

    @Override
    public boolean saveOrUpdate(Product product) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check = 0;
        try {
            if (product.getProductId() == 0) {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ADD_PRODUCT(?,?,?,?,?,?)}");
                callableStatement.setString(1, product.getProductName());
                callableStatement.setString(2, product.getDescription());
                callableStatement.setFloat(3, product.getPrice());
                callableStatement.setInt(4, product.getStock());
                callableStatement.setString(5, product.getImageUrl());
                callableStatement.setInt(6, product.getCategory().getCategoryId());
                check = callableStatement.executeUpdate();
            } else {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?)}");
                callableStatement.setInt(1, product.getProductId());
                callableStatement.setString(2, product.getProductName());
                callableStatement.setString(3, product.getDescription());
                callableStatement.setFloat(4, product.getPrice());
                callableStatement.setInt(5, product.getStock());
                callableStatement.setString(6, product.getImageUrl());
                callableStatement.setInt(7, product.getCategory().getCategoryId());
                check = callableStatement.executeUpdate();
            }
            if (check > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public void block(Integer id) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("{CALL PROC_BLOCK_PRODUCT(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public boolean checkProductName(String productName) {
        List<Product> productList = findAll();
        for (Product product : productList) {
            if(product.getProductName().equals(productName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Product> getLastestProduct() {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_LASTEST_PRODUCT}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public void updateStock(int productId, int quantity) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        CallableStatement callableStatement = null;
        try {
            callableStatement = connection.prepareCall("{CALL PROC_UPDATE_QUANTITY_PRODUCT(?,?)}");
            callableStatement.setInt(1, productId);
            callableStatement.setInt(2, quantity);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Product> showAllWithPagination(int limit, int currenPage) {
        Connection connection = null;
        List<Product> products = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_PRODUCT_PAGINATION(?,?,?)}");
            callableStatement.setInt(1,limit);
            callableStatement.setInt(2,currenPage);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
                products.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return products;
    }

    @Override
    public List<Product> findByNameWithPagination(String name, int limit, int currenPage) {
        Connection connection = null;
        List<Product> foundList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_PRODUCT_FIND_PAGED(?,?,?)}");
            String nameSearch = name.toLowerCase().trim();
            callableStatement.setString(1, nameSearch);
            callableStatement.setInt(2, limit);
            callableStatement.setInt(3, currenPage);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
                foundList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return foundList;
    }

    @Override
    public Integer getTotalPagesOnShowAll(int limit, int currenPage) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_PRODUCT_PAGINATION(?,?,?)}");
            callableStatement.setInt(1,limit);
            callableStatement.setInt(2,currenPage);
            callableStatement.registerOutParameter(3, Types.INTEGER);
            callableStatement.executeQuery();
            return callableStatement.getInt(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public Integer getTotalPagesOnSearch(int limit, String name) {
        Connection connection = null;
        List<Product> foundList = new ArrayList<>();
        int total;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_PRODUCT_BY_NAME(?)}");
            String nameSearch = name.toLowerCase().trim();
            callableStatement.setString(1, nameSearch);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("id"));
                product.setProductName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getFloat("price"));
                product.setStock(resultSet.getInt("stock"));
                product.setImageUrl(resultSet.getString("image"));
                Category category = categoryDAO.findById(resultSet.getInt("category_id"));
                product.setCategory(category);
                product.setStatus(resultSet.getBoolean("status"));
                foundList.add(product);
            }
            total = (int) Math.ceil((double) foundList.size() /limit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return total;
    }
}
