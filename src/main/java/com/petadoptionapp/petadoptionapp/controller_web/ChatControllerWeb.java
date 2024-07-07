package com.petadoptionapp.petadoptionapp.controller_web;


import com.petadoptionapp.petadoptionapp.service.OpenALService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ChatControllerWeb {

    private final OpenALService openALService;

//    @GetMapping("/chat")
//    public String getChatPage() {
//        return "chat";
//    }

    @PostMapping("/chat")
    @ResponseBody
    public String handleChat(@RequestParam String prompt) {
        return openALService.getResponse(prompt);
    }

}
