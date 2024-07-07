package com.petadoptionapp.petadoptionapp.controller_web;


import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import com.petadoptionapp.petadoptionapp.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/web/preferences")
public class PreferenceControllerWeb {

    private final PreferenceService preferenceService;

    @GetMapping("/addPreference")
    public String showAddPreferenceForm(Model model) {
        model.addAttribute("preference", new Preference());
        return "addPreference";
    }

    @PostMapping("/addPreference")
    public String addPreference(@ModelAttribute("preference") Preference preference) {
        preferenceService.save(preference);
        return "redirect:/web/preferences/showAllPreferences";
    }

    @GetMapping("/showAllPreferences")
    public String listPreferences(Model model) {
        model.addAttribute("preferences", preferenceService.getAll());
        return "showAllPreferences";
    }

    @GetMapping("/getPreferenceIdForUpdate")
    public String getPreferenceIdForUpdate(Model model) {
        return "getPreferenceId";
    }

    @GetMapping("/getPreferenceForUpdate")
    public String showUpdatePreferenceForm(@RequestParam("id") Long id, Model model) {
        Preference preference = preferenceService.getById(id);
        model.addAttribute("preference", preference);
        return "updatePreference";
    }

    @PostMapping("/updatePreference")
    public String updatePreference(@ModelAttribute("preference") Preference preference) {
        preferenceService.save(preference);
        return "redirect:/web/preferences/showAllPreferences";
    }

    @GetMapping("/getPreferenceIdForDelete")
    public String getPreferenceIdForDelete(Model model) {
        return "getPreferenceIdToDelete";
    }

    @GetMapping("/getPreferenceForDelete")
    public String showDeletePreferenceForm(@RequestParam("id") Long id, Model model) {
        Preference preference = preferenceService.getById(id);
        model.addAttribute("preference", preference);
        return "deletePreference";
    }

    @PostMapping("/deletePreference")
    public String deletePreference(@RequestParam("id") Long id) {
        preferenceService.deleteById(id);
        return "redirect:/web/preferences/showAllPreferences";
    }


}
