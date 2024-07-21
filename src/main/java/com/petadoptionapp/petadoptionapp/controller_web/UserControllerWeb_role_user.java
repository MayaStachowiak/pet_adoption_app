package com.petadoptionapp.petadoptionapp.controller_web;


import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import com.petadoptionapp.petadoptionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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



//    @PostMapping("/editProfile")
//    public String editUserProfile(@ModelAttribute("user") User user) {
//        userService.save(user);
//        return "redirect:/web/user/profile";
//    }

    @GetMapping("/editUser")
    public String editUser(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "editUserProfileForUserRole";
    }

    @PostMapping("/editProfile")
    public String updateUser(User user, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User existingUser = userService.findByUsername(userDetails.getUsername());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setPhoneNumber(user.getPhoneNumber());

        System.out.println("Wprowadzono zmiany");
        userService.save(existingUser);
        return "redirect:/user/web/confirmation";
    }

    @GetMapping("/confirmation")
    public String showConfirmationPage() {
        return "confirmation";
    }


    @GetMapping("animals/showAllAnimals")
    public String listAnimals(Model model) {
        model.addAttribute("animals", animalService.getAll());
        return "showAllAnimals";
    }

    @GetMapping("/favorites")
    public String showUserFavorites(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Set<Animal> favorites = userService.getUserFavorites(username);
        model.addAttribute("favorites", favorites);
        return "userFavorites";
    }

    @PostMapping("/addToFavorites/{animalId}")
    @ResponseBody
    public String addToFavorites(@PathVariable Long animalId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.addAnimalToFavorites(username, animalId);
        System.out.println("Dodaj do ulubionych kontroler");
        return "Dodano do ulubionych!";
    }
}