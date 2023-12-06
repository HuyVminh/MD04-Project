package com.project.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("")
    public String homeAdmin() {
        return "admin/index";
    }


    @RequestMapping("/product")
    public String productManagement() {
        return "admin/product/product";
    }
    @RequestMapping("/user")
    public String userManagement() {
        return "admin/user";
    }

}
