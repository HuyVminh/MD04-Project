package com.project.controller;

import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.model.entity.User;
import com.project.model.service.category.ICategoryService;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private HttpSession session;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;

    @RequestMapping("/")
    public String index(Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        model.addAttribute("userLogin", userLogin);
        List<Category> categories = categoryService.getCategoryList();
        session.setAttribute("categoryList", categories);
        model.addAttribute("categoryList", categories);
        List<Product> products = productService.getLastestProduct();
        model.addAttribute("productList", products);
        return "home";
    }
}
