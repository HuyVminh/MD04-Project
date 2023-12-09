package com.project.controller;

import com.project.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private HttpSession session;
    @RequestMapping("/")
    public String index(Model model){
        User userLogin = (User) session.getAttribute("userLogin");
        model.addAttribute("userLogin", userLogin);
        return "home";
    }
}
