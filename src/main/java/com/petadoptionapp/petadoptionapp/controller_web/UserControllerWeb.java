package com.petadoptionapp.petadoptionapp.controller_web;

import com.petadoptionapp.petadoptionapp.bean.dao.User;
import com.petadoptionapp.petadoptionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/users")
public class UserControllerWeb {


    private final UserService userService;

    @GetMapping("/addUser")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/web/users/showAllUsers";
    }

    @GetMapping("/showAllUsers")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "showAllUsers";
    }

    @GetMapping("/getUserIdForUpdate")
    public String getUserIdForUpdate(Model model) {
        return "getUserId";
    }

    @GetMapping("/getUserForUpdate")
    public String showUpdateUserForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/web/users/showAllUsers";
    }

    @GetMapping("/getUserIdForDelete")
    public String getUserIdForDelete(Model model) {
        return "getUserIdToDelete";
    }

    @GetMapping("/getUserForDelete")
    public String showDeleteUserForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "deleteUser";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/web/users/showAllUsers";
    }
}
