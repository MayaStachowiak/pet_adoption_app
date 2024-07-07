package com.petadoptionapp.petadoptionapp.controller_web;


import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import com.petadoptionapp.petadoptionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/web")
public class UserControllerWeb_role_user {

    private final UserService userService;
    private final AnimalService animalService;

    @GetMapping("/profile")
    public String showUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "userProfile";
    }

    @GetMapping("/editProfile")
    public String showEditUserProfileForm(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "editUserProfile";
    }

    @PostMapping("/editProfile")
    public String editUserProfile(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/web/user/profile";
    }

    @GetMapping("/favorites")
    public String showUserFavorites(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
//        model.addAttribute("favorites", user.getFavorites());
        return "userFavorites";
    }

    @GetMapping("animals/showAllAnimals")
    public String listAnimals(Model model) {
        model.addAttribute("animals", animalService.getAll());
        return "showAllAnimals";
    }
}