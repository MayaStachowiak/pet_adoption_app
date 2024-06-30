package com.petadoptionapp.petadoptionapp.controller;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/animals")
public class AnimalControllerWeb { //todo dodać id do wyświetlanej listy, naprawić przycisk w liście, przycisk gohome w update, screen w readme

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
    public String updateAnimal(@ModelAttribute("animal") Animal animal) {
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

}
