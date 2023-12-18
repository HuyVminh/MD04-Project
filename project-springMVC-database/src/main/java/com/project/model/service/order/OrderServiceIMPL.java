package com.project.model.service.order;

import com.project.model.dao.order.IOrderDAO;
import com.project.model.dao.orderDetail.IOrderDetailDAO;
import com.project.model.dao.product.IProductDAO;
import com.project.model.entity.Cart;
import com.project.model.entity.Order;
import com.project.model.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderServiceIMPL implements IOrderService {
    @Autowired
    private IOrderDAO orderDAO;
    @Autowired
    private HttpSession session;
    @Autowired
    private IOrderDetailDAO orderDetailDAO;

    @Override
    public boolean orderCheckout(Order order) {
        try {
            Order newOrder = orderDAO.findOrderById(orderDAO.saveOrder(order));
            List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
            for (Cart cart : cartList){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(newOrder);
                orderDetail.setProduct(cart.getProduct());
                orderDetail.setPrice(cart.getProduct().getPrice());
                orderDetail.setQuantity(cart.getQuantity());
                orderDetailDAO.save(orderDetail);
            }
            return true;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }

    @Override
    public Order findOrderById(Integer id) {
        return orderDAO.findOrderById(id);
    }

    @Override
    public List<OrderDetail> findDetailByOrderId(Integer id) {
        return orderDetailDAO.findDetailByOrderId(id);
    }
}
