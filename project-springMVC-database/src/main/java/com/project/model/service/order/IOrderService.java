package com.project.model.service.order;

import com.project.model.entity.Order;
import com.project.model.entity.OrderDetail;

import java.util.List;

public interface IOrderService {
    boolean orderCheckout(Order order);
    List<Order> findAll();
    Order findOrderById(Integer id);
    List<OrderDetail> findDetailByOrderId(Integer id);
}
