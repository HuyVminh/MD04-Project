package com.project.controller.admin;

import com.project.model.entity.Product;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class ProductController {
    @Autowired
    IProductService productService;
    @RequestMapping("/products")
    public String getProduct(Model model){
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/product/product";
    }
}
