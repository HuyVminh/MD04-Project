package com.project.controller.user;

import com.project.model.dto.user.UserCheckoutDTO;
import com.project.model.entity.Cart;
import com.project.model.entity.Order;
import com.project.model.entity.User;
import com.project.model.service.cart.ICartService;
import com.project.model.service.order.IOrderService;
import com.project.model.service.order.OrderServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    private ICartService cartService;
    @Autowired
    private HttpSession session;
    @Autowired
    private IOrderService orderService;

    @RequestMapping("/checkout")
    public String getCheckout(Model model,RedirectAttributes redirectAttributes) {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            return "redirect:/login-register";
        }
        UserCheckoutDTO userCheckoutDTO = new UserCheckoutDTO();
        userCheckoutDTO.setAddress(userLogin.getAddress());
        userCheckoutDTO.setFullName(userLogin.getFullName());
        userCheckoutDTO.setPhone(userLogin.getPhone());
        userCheckoutDTO.setEmail(userLogin.getEmail());
        model.addAttribute("userCheckOutDTO", userCheckoutDTO);
        List<Cart> cartList = cartService.findCartByUserId(userLogin.getUserId());
        if(cartList.isEmpty()){
            redirectAttributes.addFlashAttribute("err", "Giỏ hàng rỗng !");
            return "redirect:/cart";
        }
        model.addAttribute("cartList", cartList);
        return "user/checkout";
    }

    @PostMapping("/checkout")
    public String handleCheckout(@ModelAttribute("userCheckoutDTO") UserCheckoutDTO userCheckoutDTO, RedirectAttributes redirectAttributes) {
        List<Cart> cartList = (List<Cart>) session.getAttribute("cartList");
        boolean check = true;
        String p_name = "";
        for (Cart cart : cartList) {
            if (cart.getQuantity() > cart.getProduct().getStock()) {
                check = false;
                p_name = cart.getProduct().getProductName();
            }
        }
        if (check){
            Order newOrder = new Order();
            newOrder.setOrderCustomerName(userCheckoutDTO.getFullName());
            newOrder.setNote(userCheckoutDTO.getNote());
            newOrder.setAddress(userCheckoutDTO.getAddress());
            newOrder.setPhoneNumber(userCheckoutDTO.getPhone());
            User userLogin = (User) session.getAttribute("userLogin");
            newOrder.setUser(userLogin);
            newOrder.setTotal((Float) session.getAttribute("total"));
            orderService.orderCheckout(newOrder);
            cartService.removeCartByUserId(userLogin.getUserId());
            return "redirect:/";
        }else {
            redirectAttributes.addFlashAttribute("err", "Sản phẩm " + p_name + " không đủ số lượng trong kho !");
            return "redirect:/cart";
        }
    }
}
