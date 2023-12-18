package com.project.model.dao.orderDetail;

import com.project.model.dao.order.IOrderDAO;
import com.project.model.dao.product.IProductDAO;
import com.project.model.entity.Category;
import com.project.model.entity.Order;
import com.project.model.entity.OrderDetail;
import com.project.model.entity.Product;
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
public class OrderDetailDaoIMPL implements IOrderDetailDAO{
    @Autowired
    private IOrderDAO orderDAO;
    @Autowired
    private IProductDAO productDAO;
    @Override
    public void save(OrderDetail orderDetail) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SAVE_ORDER_DETAIL(?,?,?,?)}");
            callableStatement.setInt(1, orderDetail.getOrder().getOrderId());
            callableStatement.setInt(2, orderDetail.getProduct().getProductId());
            callableStatement.setFloat(3, orderDetail.getPrice());
            callableStatement.setInt(4, orderDetail.getQuantity());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<OrderDetail> findDetailByOrderId(Integer id) {
        Connection connection = null;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_SHOW_ORDER_DETAIL(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(resultSet.getInt("id"));
                Order order = orderDAO.findOrderById(resultSet.getInt("order_id"));
                orderDetail.setOrder(order);
                Product product = productDAO.findById(resultSet.getInt("product_id"));
                orderDetail.setProduct(product);
                orderDetail.setPrice(resultSet.getFloat("price"));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderDetailList;
    }
}
