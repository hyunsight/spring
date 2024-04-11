package com.example.myLibrary.controller;

import com.example.myLibrary.dto.User;
import com.example.myLibrary.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/login")
    public String login() {
        return "user/login";
    }

    @PostMapping(value = "/login")
    public String loginUser(User user, HttpSession session) {
        try {
            User loginUser = userService.loginUser(user);

            if (loginUser != null) {
                session.setAttribute("id", loginUser.getId());
                session.setAttribute("userName", loginUser.getUserName());
                session.setAttribute("userGrade", loginUser.getUserGrade());
                return "redirect:/";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

        return "user/login";
    }

    @GetMapping(value = "/logout")
    public String logoutUser(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        session.removeAttribute("userGrade");

        return "redirect:/";
    }

}
