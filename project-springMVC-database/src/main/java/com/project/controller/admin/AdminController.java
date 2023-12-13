package com.project.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    HttpSession session;
    @RequestMapping("")
    public String homeAdmin() {
        return "admin/index";
    }
    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("adminLogin");
        return "redirect:/login-register";
    }
}
