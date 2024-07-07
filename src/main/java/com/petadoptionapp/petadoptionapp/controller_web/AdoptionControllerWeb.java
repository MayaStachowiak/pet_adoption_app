package com.petadoptionapp.petadoptionapp.controller_web;


import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import com.petadoptionapp.petadoptionapp.service.AdoptionService;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import com.petadoptionapp.petadoptionapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/adoptions")
public class AdoptionControllerWeb {

    private final AdoptionService adoptionService;
    private final UserService userService;
    private final AnimalService animalService;

    @GetMapping("/addAdoption")
    public String showAddAdoptionForm(Model model) {
        model.addAttribute("adoption", new Adoption());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("animals", animalService.getAll());
        return "addAdoption";
    }

    @PostMapping("/addAdoption")
    public String addAdoption(@ModelAttribute("adoption") Adoption adoption) {
        adoptionService.save(adoption);
        return "redirect:/web/adoptions/showAllAdoptions";
    }

    @GetMapping("/showAllAdoptions")
    public String listAdoptions(Model model) {
        model.addAttribute("adoptions", adoptionService.getAll());
        return "showAllAdoptions";
    }

    @GetMapping("/getAdoptionIdForUpdate")
    public String getAdoptionIdForUpdate(Model model) {
        return "getAdoptionId";
    }

    @GetMapping("/getAdoptionForUpdate")
    public String showUpdateAdoptionForm(@RequestParam("id") Long id, Model model) {
        Adoption adoption = adoptionService.getById(id);
        model.addAttribute("adoption", adoption);
        return "updateAdoption";
    }

    @PostMapping("/updateAdoption")
    public String updateAdoption(@ModelAttribute("adoption") Adoption adoption) {
        adoptionService.save(adoption);
        return "redirect:/web/adoptions/showAllAdoptions";
    }

    @GetMapping("/getAdoptionIdForDelete")
    public String getAdoptionIdForDelete(Model model) {
        return "getAdoptionIdToDelete";
    }

    @GetMapping("/getAdoptionForDelete")
    public String showDeleteAdoptionForm(@RequestParam("id") Long id, Model model) {
        Adoption adoption = adoptionService.getById(id);
        model.addAttribute("adoption", adoption);
        return "deleteAdoption";
    }

    @PostMapping("/deleteAdoption")
    public String deleteAdoption(@RequestParam("id") Long id) {
        adoptionService.deleteById(id);
        return "redirect:/web/adoptions/showAllAdoptions";
    }


}
