package com.project.model.dao.orderDetail;

import com.project.model.entity.OrderDetail;

import java.util.List;

public interface IOrderDetailDAO {
    void save(OrderDetail orderDetail);
    List<OrderDetail> findDetailByOrderId(Integer id);
}
