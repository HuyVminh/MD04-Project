package com.project.controller.user;

import com.project.model.dto.user.UserRegisterDTO;
import com.project.model.entity.User;
import com.project.model.service.user.IUserService;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpSession session;

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

    @GetMapping("/login-register")
    public String getLoginRegister(Model model) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("user", userRegisterDTO);
        return "user/login-register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserRegisterDTO user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "/user/login-register";
        } else {
            if (!user.isPasswordMatch()) {
                result.rejectValue("confirmPassword", "error.register", "Mật khẩu không khớp");
                return "/user/login-register";
            } else {
                if (userService.register(user)) {
                    User user1 = new ModelMapper().map(user, User.class);
                    session.setAttribute("userLogin", user1);
                    return "redirect:/";
                }
            }
        }
        return "redirect:/login-register";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("user") UserRegisterDTO user, RedirectAttributes redirectAttributes) {
        User user_check = userService.findByEmail(user.getEmail());
        if (user.getEmail().equals(user_check.getEmail()) && BCrypt.checkpw(user.getPassword(), user_check.getPassword()) && user_check.isRole()) {
            session.setAttribute("userLogin", user_check);
            return "redirect:/";
        }
        redirectAttributes.addFlashAttribute("err", "Email hoặc mật khẩu không đúng !");
        return "redirect:/login-register";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("userLogin");
        return "redirect:/";
    }
}
