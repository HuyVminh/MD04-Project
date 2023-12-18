package com.project.controller.user;

import com.project.model.dto.user.UserRegisterDTO;
import com.project.model.entity.*;
import com.project.model.service.cart.ICartService;
import com.project.model.service.category.ICategoryService;
import com.project.model.service.product.IProductService;
import com.project.model.service.user.IUserService;
import com.project.model.service.wishlist.IWishlishService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private IWishlishService wishlishService;
    @Autowired
    private ICartService cartService;

    @GetMapping("/list-product")
    public String getListProduct(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("productList", productList);
        List<Category> categories = categoryService.getCategoryList();
        model.addAttribute("categoryList", categories);
        return "user/list-product";
    }

    @GetMapping("/category/{id}")
    public String getListProductById(@PathVariable("id") Integer id, Model model) {
        List<Product> productList = productService.findAll();
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            if (product.getCategory().getCategoryId() == id) {
                products.add(product);
            }
        }
        model.addAttribute("productList", products);
        Category category = categoryService.findById(id);
        model.addAttribute("category", category);
        List<Category> categories = categoryService.getCategoryList();
        model.addAttribute("categoryList", categories);
        return "user/list-product";
    }


    @RequestMapping("/wishlist")
    public String getWishlist(Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin != null) {
            List<Wishlist> list = wishlishService.findAllByUserId(userLogin.getUserId());
            model.addAttribute("wishlist", list);
        }
        return "user/wishlist";
    }

    @GetMapping("/wishlist/{id}")
    public String addWishlist(@PathVariable("id") int id) {
        System.out.println(id);
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin == null) {
            return "redirect:/login-register";
        }else {
            if(!wishlishService.checkProductInWishlist(userLogin.getUserId(),id)){
                wishlishService.addToWishlist(userLogin.getUserId(), id);
            }else {
                wishlishService.removeFromWishlist(userLogin.getUserId(),id);
            }
            return "redirect:/wishlist";

        }
    }

    @GetMapping("/login-register")
    public String getLoginRegister(Model model) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("user", userRegisterDTO);
        List<Category> categories = categoryService.getCategoryList();
        model.addAttribute("categoryList", categories);
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
                if (user_check.isStatus()) {
                    session.setAttribute("userLogin", user_check);
                    List<Category> categories = categoryService.getCategoryList();
                    session.setAttribute("categoryList", categories);
                    List<Cart> cartList = cartService.findCartByUserId(user_check.getUserId());
                    session.setAttribute("cartList", cartList);
                    float total = 0;
                    for (Cart cart : cartList) {
                        total += cart.getQuantity()*cart.getProduct().getPrice();
                    }
                    session.setAttribute("total",total);
                    return "redirect:/";
                } else {
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
    public String productDetail(@PathVariable("id") int id, Model model) {
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "user/product-detail";
    }

    @RequestMapping("/my-account")
    public String getMyAccount(Model model) {
        User userLogin = (User) session.getAttribute("userLogin");
        model.addAttribute("userLogin", userLogin);
        return "user/my-account";
    }

    @PostMapping("/updateUserLogin")
    public String updateInformation(@RequestParam("avatarImage") MultipartFile file, @ModelAttribute("userLogin") User userLogin, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "user/my-account";
        }
        String fileName = file.getOriginalFilename();
        File destination = new File(path + fileName);

        try {
            if (!fileName.isEmpty()) {
                file.transferTo(destination);
                userLogin.setAvatar("http://localhost:8080/uploads/avatars/" + fileName);
            }
            User user = (User) session.getAttribute("userLogin");
            System.out.println(user.getPassword());
            if (BCrypt.checkpw(userLogin.getPassword(), user.getPassword())) {
                if (userService.update(userLogin)) {
                    session.setAttribute("userLogin", userLogin);
                    redirectAttributes.addFlashAttribute("mess", "Cập nhật thông tin thành công !");
                    return "redirect:/my-account";
                }
            } else {
                redirectAttributes.addFlashAttribute("err", "Mật khẩu không khớp !");
                return "redirect:/my-account";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "user/my-account";
    }
}
