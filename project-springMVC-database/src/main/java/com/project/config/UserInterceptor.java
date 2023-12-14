package com.project.config;

import com.project.model.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User userLogin = (User) session.getAttribute("userLogin");
        if (userLogin != null) {
            return true;
        }
        response.sendRedirect("/login-register");
        return false;
    }
}
