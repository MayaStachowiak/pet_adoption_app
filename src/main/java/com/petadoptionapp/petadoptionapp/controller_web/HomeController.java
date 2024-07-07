package com.petadoptionapp.petadoptionapp.controller_web;

import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/admin/home")
    public String home(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("currentDateTime", formattedDateTime);
        model.addAttribute("welcomeMessage", "Adoptuj zwierzaka!");
        return "main";
    }

    //---------------------------------------------------------------
    @GetMapping("/home")
    public String homeAdmin(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("currentDateTime", formattedDateTime);
        model.addAttribute("welcomeMessage", "Adoptuj zwierzaka!");
        return "home";
    }

    @GetMapping("/user/home")
    public String homeUser(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("currentDateTime", formattedDateTime);
        model.addAttribute("welcomeMessage", "Adoptuj zwierzaka!");
        return " ";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        System.out.println("get login");
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication authentication) {
        System.out.println("Ogólny");
        System.out.println("ROla " + authentication.getAuthorities());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                return "redirect:/admin/home";
            } else if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                System.out.println("rola user");
                return "redirect:/user/home";
            }
        }
        return "redirect:/default";
    }
}
