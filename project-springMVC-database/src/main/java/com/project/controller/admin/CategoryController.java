package com.project.controller.admin;

import com.project.model.entity.Category;
import com.project.model.entity.Product;
import com.project.model.service.category.ICategoryService;
import com.project.model.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IProductService productService;

    @RequestMapping("/category")
    public String categoryManagement(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/category/category";
    }

    @GetMapping("/category/add-category")
    public String openAddModal(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/add-category";
    }

    @PostMapping("/category/add-category")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            return "admin/category/add-category";
        }
        if(categoryService.checkCategoryName(category.getCategoryName())){
            redirectAttributes.addFlashAttribute("err", "Đã tồn tại danh mục !");
            return "redirect:/admin/category/add-category";
        }
        categoryService.saveOrUpdate(category);
        redirectAttributes.addFlashAttribute("mess", "Thêm mới danh mục thành công !");
        return "redirect:/admin/category";
    }

    @GetMapping("/category/edit-category/{id}")
    public String editCategory(@PathVariable("id") Integer id, Model model) {
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/category/edit-category";
    }

    @PostMapping("/category/edit-category")
    public String update(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes,BindingResult result) {
        if (result.hasErrors()){
            return "admin/category/edit-category";
        }
        categoryService.saveOrUpdate(category);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công !");
        return "redirect:/admin/category";
    }

    @GetMapping("/category/{id}")
    public String blockCategory(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        if(!categoryService.checkProductByCategoryId(id)){
            redirectAttributes.addFlashAttribute("err", "Danh mục đang tồn tại sản phẩm, không thể block !");
            return "redirect:/admin/category";
        }
        categoryService.block(id);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công !");
        return "redirect:/admin/category";
    }

    @PostMapping("/category/search")
    public String search(@RequestParam("nameSearch") String name, Model model) {
        List<Category> categories = categoryService.findByName(name);
        model.addAttribute("categories", categories);
        return "admin/category/category";
    }
}
