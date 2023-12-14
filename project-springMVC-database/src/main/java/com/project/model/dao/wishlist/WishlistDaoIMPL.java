package com.project.model.dao.wishlist;

import com.project.model.dao.category.ICategoryDAO;
import com.project.model.dao.product.IProductDAO;
import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.model.entity.Wishlist;
import com.project.model.service.product.IProductService;
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
public class WishlistDaoIMPL implements IWishlistDAO {
    @Autowired
    private ICategoryDAO categoryDAO;
    @Autowired
    private IProductDAO productDAO;
    @Override
    public List<Wishlist> findAllByUserId(Integer id) {
        Connection connection = null;
        List<Wishlist> foundList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_WISHLIST_BY_USER_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setId(resultSet.getInt("id"));
                wishlist.setUserId(resultSet.getInt("user_id"));
                Product product = productDAO.findById(resultSet.getInt("product_id"));
                wishlist.setProduct(product);
                foundList.add(wishlist);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return foundList;
    }

    @Override
    public boolean addToWishlist(int userId,int productId) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check = 0;
        try {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ADD_TO_WISHLIST(?,?)}");
                callableStatement.setInt(1, userId);
                callableStatement.setInt(2, productId);
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
    public boolean removeFromWishlist(int userId,int productId) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int check = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_REMOVE_FROM_WISHLIST(?,?)}");
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, productId);
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
