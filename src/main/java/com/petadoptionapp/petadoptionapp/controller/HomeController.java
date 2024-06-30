package com.petadoptionapp.petadoptionapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class HomeController {


    @GetMapping("/")
    public String home(Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        model.addAttribute("currentDateTime", formattedDateTime);
        model.addAttribute("welcomeMessage", "Adoptuj zwierzaka!");
        return "main";
    }
}
