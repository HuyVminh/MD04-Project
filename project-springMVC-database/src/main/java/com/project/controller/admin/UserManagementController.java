package com.project.controller.admin;

import com.project.model.entity.User;
import com.project.model.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserManagementController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/user")
    public String userManagement(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("userList", userList);
        return "admin/user/user";
    }

    @GetMapping("/user/{id}")
    public String blockUser(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        userService.block(id);
        redirectAttributes.addFlashAttribute("mess", "Cập nhật thành công !");
        return "redirect:/admin/user";
    }
    @PostMapping("/user/search")
    public String search(@RequestParam("emailSearch") String email, Model model) {
        List<User> userList = userService.searchUser(email);
        model.addAttribute("userList", userList);
        return "admin/user/user";
    }
}
