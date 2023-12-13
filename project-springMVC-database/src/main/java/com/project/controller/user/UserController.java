package com.project.controller.user;

import com.project.model.dto.user.UserRegisterDTO;
import com.project.model.entity.Product;
import com.project.model.entity.User;
import com.project.model.service.product.IProductService;
import com.project.model.service.user.IUserService;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@PropertySource("classpath:config.properties")
public class UserController {
    @Value("${path_avatar}")
    private String path;
    @Autowired
    private IUserService userService;
    @Autowired
    private HttpSession session;
    @Autowired
    private IProductService productService;

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
    public String getListProduct(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        return "user/list-product";
    }

    @RequestMapping("/my-account")
    public String getMyAccount() {
        return "user/my-account";
    }

    @RequestMapping("/wishlist")
    public String getWishlist() {
        return "user/wishlist";
    }

    @GetMapping("/login-register")
    public String getLoginRegister(Model model) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("user", userRegisterDTO);
        return "user/login-register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("user") UserRegisterDTO user, BindingResult result, HttpSession session, MultipartFile file) {
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
        if (user_check.getEmail() != null) {
            if (BCrypt.checkpw(user.getPassword(), user_check.getPassword()) && !user_check.isRole()) {
                session.setAttribute("adminLogin", user_check);
                return "redirect:/admin";
            }
            if (BCrypt.checkpw(user.getPassword(), user_check.getPassword()) && user_check.isRole()) {
                if(user_check.isStatus()){
                    session.setAttribute("userLogin", user_check);
                    return "redirect:/";
                }else {
                    redirectAttributes.addFlashAttribute("err", "Tài khoản của bạn đã bị khóa !");
                    return "redirect:/login-register";
                }
            }
            redirectAttributes.addFlashAttribute("err", "Email hoặc mật khẩu không đúng !");
            return "redirect:/login-register";
        }
        redirectAttributes.addFlashAttribute("err", "Email hoặc mật khẩu không đúng !");
        return "redirect:/login-register";
    }

    @GetMapping("/logout")
    public String logout() {
        session.removeAttribute("userLogin");
        return "redirect:/";
    }
    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable("id") int id,Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "user/product-detail";
    }
}
