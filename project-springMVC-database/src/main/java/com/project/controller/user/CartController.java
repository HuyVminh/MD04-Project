package com.project.controller.user;

import com.project.model.dao.cart.ICartDAO;
import com.project.model.entity.Cart;
import com.project.model.entity.Product;
import com.project.model.entity.User;
import com.project.model.service.cart.ICartService;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    HttpSession session;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IProductService productService;

    @GetMapping("/cart")
    public String getCart() {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            return "redirect:/login-register";
        } else {
            cartService.findCartByUserId(userLogin.getUserId());
            return "user/cart";
        }
    }

    @GetMapping("/fast-add/{id}")
    public String fastAddToCart(@PathVariable("id") int id) {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            return "redirect:/login-register";
        } else {
           if(cartService.checkProductInCart(userLogin.getUserId(),id)){
               Product product = productService.findById(id);
              cartService.updateCart(product, userLogin.getUserId(), 1);
           }else {
               Product product = productService.findById(id);
               cartService.addToCart(product, userLogin.getUserId(), 1);
           }
            return "redirect:/cart";
        }
    }

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam("productId") int productId, @RequestParam("quantity") int quantity, Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            return "redirect:/login-register";
        } else {
            if(cartService.checkProductInCart(userLogin.getUserId(),productId)){
                Product product = productService.findById(productId);
                cartService.updateCart(product, userLogin.getUserId(), quantity);
            }else {
                Product product = productService.findById(productId);
                cartService.addToCart(product, userLogin.getUserId(), quantity);
            }
            return "redirect:/cart";
        }
    }

    @GetMapping("/remove-item/{id}")
    public String removeCart(@PathVariable("id") int id) {
        cartService.removeFromCart(id);
        return "redirect:/cart";
    }

    @PostMapping("/update-cart-by-id")
    public String updateCartById(@RequestParam("cartId") int cartId, @RequestParam("qty") int qty) {

        if (qty > 0) {
            cartService.updateCartById(cartId, qty);
        } else {
            cartService.removeFromCart(cartId);
        }
        return "redirect:/cart";
    }
}
