package com.project.controller.admin;

import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.model.service.category.ICategoryService;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@PropertySource("classpath:config.properties")
@RequestMapping("/admin")
public class ProductController {
    @Value("${path_image}")
    private String path;
    @Autowired
    private IProductService productService;
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    ServletContext servletContext;

    @RequestMapping("/product")
    public String getProduct(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/product/product";
    }

    @GetMapping("/product/add-product")
    public String openAddProduct(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll();
        List<Category> categoryListTrue = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.isStatus()) {
                categoryListTrue.add(category);
            }
        }
        model.addAttribute("categoryList", categoryListTrue);
        return "admin/product/add-product";
    }

    @PostMapping("/product/add-product")
    public String createProduct(@Valid @ModelAttribute("product") Product product, BindingResult result, @RequestParam("imageProduct") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            List<Category> categoryList = categoryService.findAll();
            List<Category> categoryListTrue = new ArrayList<>();
            for (Category category : categoryList) {
                if (category.isStatus()) {
                    categoryListTrue.add(category);
                }
            }
            model.addAttribute("categoryList", categoryListTrue);
            return "admin/product/add-product";
        }
        String fileName = file.getOriginalFilename();
        File destination = new File(path + fileName);
        try {
            file.transferTo(destination);
            product.setImageUrl("http://localhost:8080/uploads/images/" + fileName);

            if (productService.saveOrUpdate(product)) {
                redirectAttributes.addFlashAttribute("mess", "Thêm mới sản phẩm thành công !");
                return "redirect:/admin/product";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/product/edit-product/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        List<Category> categoryList = categoryService.findAll();
        List<Category> categoryListTrue = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.isStatus()) {
                categoryListTrue.add(category);
            }
        }
        model.addAttribute("categoryList", categoryListTrue);
        return "admin/product/edit-product";
    }

    @PostMapping("/product/edit-product")
    public String update(@Valid @ModelAttribute("product") Product product, BindingResult result, @RequestParam("imageProduct") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        if (result.hasErrors()) {
            List<Category> categoryList = categoryService.findAll();
            List<Category> categoryListTrue = new ArrayList<>();
            for (Category category : categoryList) {
                if (category.isStatus()) {
                    categoryListTrue.add(category);
                }
            }
            model.addAttribute("categoryList", categoryListTrue);
            return "admin/product/edit-product";
        }
        String fileName = file.getOriginalFilename();
        File destination = new File(path + fileName);
        try {
            if (!fileName.isEmpty()) {
                file.transferTo(destination);
                product.setImageUrl("http://localhost:8080/uploads/images/" + fileName);
            }
            productService.saveOrUpdate(product);
            redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công !");
            return "redirect:/admin/product";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/product/{id}")
    public String blockProduct(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        productService.block(id);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công !");
        return "redirect:/admin/product";
    }
}
