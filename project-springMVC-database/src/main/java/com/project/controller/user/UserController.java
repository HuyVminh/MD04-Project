package com.project.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping("/about")
    public String getAbout() {
        return "user/about";
    }

    @RequestMapping("/contact")
    public String getContact() {
        return "user/contact";
    }
    @RequestMapping("/cart")
    public String getCart() {
        return "user/cart";
    }
    @RequestMapping("/checkout")
    public String getCheckout() {
        return "user/checkout";
    }
    @RequestMapping("/list-product")
    public String getListProduct() {
        return "user/list-product";
    }
    
}
