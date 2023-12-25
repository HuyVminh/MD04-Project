package com.project.model.dao.order;

import com.project.model.dao.user.IUserDAO;
import com.project.model.entity.Order;
import com.project.model.entity.User;
import com.project.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoIMPL implements IOrderDAO {
    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<Order> findAll() {
        Connection connection = null;
        List<Order> orderList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_ORDER}");
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                User user = userDAO.findById(resultSet.getInt("user_id"));
                order.setUser(user);
                order.setOrderCustomerName(resultSet.getString("order_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhoneNumber(resultSet.getString("phone"));
                order.setNote(resultSet.getString("note"));
                order.setCreated_date(resultSet.getDate("created_date"));
                order.setStatus(resultSet.getInt("status"));
                order.setTotal(resultSet.getFloat("total"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderList;
    }

    @Override
    public Integer saveOrder(Order order) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        int orderId = 0;
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SAVE_ORDER(?,?,?,?,?,?,?)}");
            callableStatement.setInt(1, order.getUser().getUserId());
            callableStatement.setString(2, order.getOrderCustomerName());
            callableStatement.setString(3, order.getAddress());
            callableStatement.setString(4, order.getPhoneNumber());
            callableStatement.setString(5, order.getNote());
            callableStatement.setFloat(6, order.getTotal());
            callableStatement.registerOutParameter(7, Types.INTEGER);

            if (callableStatement.executeUpdate() > 0) {
                orderId = callableStatement.getInt(7);
                return orderId;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return 0;
    }

    @Override
    public Order findOrderById(Integer id) {
        Connection connection = null;
        Order order = new Order();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_FIND_ORDER_BY_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            if (resultSet.next()) {
                order.setOrderId(resultSet.getInt("id"));
                User user = userDAO.findById(resultSet.getInt("user_id"));
                order.setUser(user);
                order.setOrderCustomerName(resultSet.getString("order_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhoneNumber(resultSet.getString("phone"));
                order.setNote(resultSet.getString("note"));
                order.setCreated_date(resultSet.getDate("created_date"));
                order.setStatus(resultSet.getInt("status"));
                order.setTotal(resultSet.getFloat("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return order;
    }

    @Override
    public void acceptOrder(int orderId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_ACCEPT_STATUS_ORDER(?)}");
            callableStatement.setInt(1, orderId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void cancleOrder(int orderId) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_DENIDE_STATUS_ORDER(?)}");
            callableStatement.setInt(1, orderId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Order> findOrderByUserId(int userId) {
        Connection connection = null;
        List<Order> orderList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_ORDER_BY_USER_ID(?)}");
            callableStatement.setInt(1, userId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("id"));
                User user = userDAO.findById(resultSet.getInt("user_id"));
                order.setUser(user);
                order.setOrderCustomerName(resultSet.getString("order_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhoneNumber(resultSet.getString("phone"));
                order.setNote(resultSet.getString("note"));
                order.setCreated_date(resultSet.getDate("created_date"));
                order.setStatus(resultSet.getInt("status"));
                order.setTotal(resultSet.getFloat("total"));
                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderList;
    }
}
