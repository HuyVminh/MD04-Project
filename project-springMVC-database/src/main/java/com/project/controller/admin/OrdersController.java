package com.project.controller.admin;

import com.project.model.entity.Order;
import com.project.model.entity.OrderDetail;
import com.project.model.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrdersController {
    @Autowired
    private IOrderService orderService;

    @GetMapping("/orders")
    public String getOrder(Model model) {
        List<Order> orderList = orderService.findAll();
        model.addAttribute("orderList", orderList);
        return "/admin/orders/orders";
    }

    @GetMapping("/orders/details/{id}")
    public String getOrderDetails(@PathVariable("id") int id, Model model) {
        List<OrderDetail> orderDetailList = orderService.findDetailByOrderId(id);
        Order order = orderService.findOrderById(id);
        model.addAttribute("orderDetailList", orderDetailList);
        model.addAttribute("order", order);
        return "/admin/orders/ordersDetails";
    }
    @GetMapping("/orders/accept/{id}")
    public String handleAcceptOrder(@PathVariable("id") int id){
        orderService.acceptOrder(id);
        return "redirect:/admin/orders";
    }
    @GetMapping("orders/cancel/{id}")
    public String handleCancelOrder(@PathVariable("id") int id){
        orderService.cancleOrder(id);
        return "redirect:/admin/orders";
    }
}
