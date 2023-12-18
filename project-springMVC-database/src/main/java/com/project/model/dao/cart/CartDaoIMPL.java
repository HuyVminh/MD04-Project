package com.project.model.dao.cart;

import com.project.model.dao.product.IProductDAO;
import com.project.model.entity.Cart;
import com.project.model.entity.Product;
import com.project.model.entity.Wishlist;
import com.project.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDaoIMPL implements ICartDAO {
    @Autowired
    private IProductDAO productDAO;

    @Override
    public List<Cart> findCartByUserId(int userId) {
        Connection connection = null;
        List<Cart> listCart = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_CART_BY_USER_ID(?)}");
            callableStatement.setInt(1, userId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setCardId(resultSet.getInt("id"));
                cart.setUserId(resultSet.getInt("user_id"));
                Product product = productDAO.findById(resultSet.getInt("product_id"));
                cart.setProduct(product);
                cart.setQuantity(resultSet.getInt("quantity"));
                listCart.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return listCart;
    }

    @Override
    public boolean addToCart(Product product, int user_id, int quantity) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ADD_TO_CART(?,?,?)}");
            callableStatement.setInt(1, user_id);
            callableStatement.setInt(2, product.getProductId());
            callableStatement.setInt(3, quantity);
            check = callableStatement.executeUpdate();
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
    public void removeFromCart(int cart_id) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_DELETE_CART_BY_ID(?)}");
            callableStatement.setInt(1, cart_id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void removeCartByUserId(int user_id) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_DELETE_CART(?)}");
            callableStatement.setInt(1, user_id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void updateCartById(int cart_id, int qty) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_CART(?,?)}");
            callableStatement.setInt(1, cart_id);
            callableStatement.setInt(2, qty);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public boolean updateCart(Product product, int user_id, int quantity) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_UPDATE_CART_DUPLICATE(?,?,?)}");
            callableStatement.setInt(1, user_id);
            callableStatement.setInt(2, product.getProductId());
            callableStatement.setInt(3, quantity);
            check = callableStatement.executeUpdate();
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
}
