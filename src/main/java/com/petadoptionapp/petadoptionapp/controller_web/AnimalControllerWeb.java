package com.petadoptionapp.petadoptionapp.controller_web;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/animals")
public class AnimalControllerWeb { //todo naprawić przycisk w liście, screen w readme

    private final AnimalService animalService;


    @GetMapping("/addAnimal")
    public String showAddAnimalForm(Model model) {
        model.addAttribute("animal", new Animal());
        return "addAnimal";
    }


    @PostMapping("/addAnimal")
    public String addAnimal(@ModelAttribute("animal") Animal animal) {
        animalService.save(animal);
        return "redirect:/web/animals/showAllAnimals";
    }


    @GetMapping("/showAllAnimals")
    public String listAnimals(Model model) {
        model.addAttribute("animals", animalService.getAll());
        return "showAllAnimals";
    }


    @GetMapping("/getAnimalIdForUpdate")
    public String getAnimalIdForUpdate(Model model) {
        return "getAnimalId";
    }


    @GetMapping("/getAnimalForUpdate")
    public String showUpdateAnimalForm(@RequestParam("id") Long id, Model model) {
        Animal animal = animalService.getById(id);
        model.addAttribute("animal", animal);
        return "updateAnimal";
    }


    @PostMapping("/updateAnimal")
    public String updateAnimal(@ModelAttribute("animal") Animal animal) throws IOException {
        animalService.save(animal);
        return "redirect:/web/animals/showAllAnimals";
    }

    @GetMapping("/getAnimalIdForDelete")
    public String getAnimalIdForDelete(Model model) {
        return "getAnimalIdToDelete";
    }

    @GetMapping("/getAnimalForDelete")
    public String showDeleteAnimalForm(@RequestParam("id") Long id, Model model) {
        Animal animal = animalService.getById(id);
        model.addAttribute("animal", animal);
        return "deleteAnimal";
    }


    @PostMapping("/deleteAnimal")
    public String deleteAnimal(@RequestParam("id") Long id) {
        animalService.deleteById(id);
        return "redirect:/web/animals/showAllAnimals";
    }

//    @GetMapping("/image/{id}")
//    @ResponseBody
//    public byte[] getImage(@PathVariable Long id) {
//        Animal animal = animalService.getById(id);
//        return animal.getImage();
//    }

    @GetMapping("/checkImages")
    @ResponseBody
    public String checkImages() {
        List<Animal> animals = animalService.getAll();
        StringBuilder result = new StringBuilder();
        for (Animal animal : animals) {
            result.append(animal.getImage()).append(" - ");
            try {
                URL url = new URL(animal.getImage());
                HttpURLConnection huc = (HttpURLConnection) url.openConnection();
                huc.setRequestMethod("HEAD");
                int responseCode = huc.getResponseCode();
                result.append(responseCode).append("<br>");
            } catch (Exception e) {
                result.append("Invalid URL").append("<br>");
            }
        }
        return result.toString();
    }

}
