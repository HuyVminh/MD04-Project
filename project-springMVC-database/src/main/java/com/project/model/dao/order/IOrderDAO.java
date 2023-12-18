package com.project.model.dao.order;

import com.project.model.entity.Order;

import java.util.List;

public interface IOrderDAO {
    List<Order> findAll();
    Integer saveOrder(Order order);
    Order findOrderById(Integer id);
    void acceptOrder(int orderId);
    void cancleOrder(int orderId);
    List<Order> findOrderByUserId(int userId);
}
