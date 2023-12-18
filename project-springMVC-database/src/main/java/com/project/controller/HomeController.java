package com.project.controller;

import com.project.model.entity.Cart;
import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.model.entity.User;
import com.project.model.service.cart.ICartService;
import com.project.model.service.category.ICategoryService;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class HomeController {
    @Autowired
    private HttpSession session;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICartService cartService;

    @RequestMapping("/")
    public String index(Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin != null){
            model.addAttribute("userLogin", userLogin);
            List<Cart> cartList = cartService.findCartByUserId(userLogin.getUserId());
            session.setAttribute("cartList", cartList);
        }
        List<Category> categories = categoryService.getCategoryList();
        session.setAttribute("categoryList", categories);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        List<Product> productList = productService.getLastestProduct();
        model.addAttribute("productList", productList);
        return "home";
    }
    @RequestMapping("/about")
    public String getAbout() {
        return "user/about";
    }

    @RequestMapping("/contact")
    public String getContact() {
        return "user/contact";
    }

    @RequestMapping("/faq")
    public String getFaq() {
        return "user/faq";
    }

    @RequestMapping("/blogs")
    public String getBlog() {
        return "user/blog";
    }
    @PostMapping("/search-products")
    public String handleSearchProducts(@RequestParam("searchProduct") String searchProduct,Model model){
        List<Product> productList = productService.findByName(searchProduct);
        model.addAttribute("productList",productList);
        return "/user/list-product";
    }
}
