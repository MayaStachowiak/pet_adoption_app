package com.petadoptionapp.petadoptionapp.controller_web;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @PostMapping("/addAnimal") //todo obsłuzyc ioexception w AdviceContr
    public String addAnimal(@ModelAttribute("animal") Animal animal, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        animal.setImage(imageFile.getBytes());
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


    @PostMapping("/updateAnimal") //todo osbluzyć IOExc
    public String updateAnimal(@ModelAttribute("animal") Animal animal, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        if (!imageFile.isEmpty() && !(imageFile.getSize() < 10485760)) { // 10 MB limit todo
            System.out.println("Jestem tu");
            animal.setImage(imageFile.getBytes());
        } else {
            System.out.println("Za duży plik");
        }

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

    @GetMapping("/image/{id}")
    @ResponseBody
    public byte[] getImage(@PathVariable Long id) {
        Animal animal = animalService.getById(id);
        return animal.getImage();
    }

}
