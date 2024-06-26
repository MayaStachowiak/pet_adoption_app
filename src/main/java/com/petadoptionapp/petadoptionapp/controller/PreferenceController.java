package com.petadoptionapp.petadoptionapp.controller;


import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import com.petadoptionapp.petadoptionapp.mapper.PreferenceMapper;
import com.petadoptionapp.petadoptionapp.service.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.PreferenceDto;
import org.openapitools.model.PreferencesIdGet404ResponseDto;
import org.openapitools.model.PreferencesPost200ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;
    private final PreferenceMapper preferenceMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PreferencesPost200ResponseDto> savePreference(@RequestBody PreferenceDto preferenceDto) {
        Preference preferenceToSave = preferenceService.save(preferenceMapper.map(preferenceDto));
        PreferenceDto preferenceToSaveDto = preferenceMapper.map(preferenceToSave);
        PreferencesPost200ResponseDto responseDto = new PreferencesPost200ResponseDto().preference(preferenceToSaveDto);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPreferenceById(@PathVariable Long id) {
        PreferenceDto preferenceDto = preferenceMapper.map(preferenceService.getById(id));
        if (preferenceDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new PreferencesIdGet404ResponseDto());
        }
        return ResponseEntity.ok(preferenceDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePreferenceById(@PathVariable Long id) {
        preferenceService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public PreferenceDto updatePreferenceById(@RequestBody PreferenceDto preferenceDto, @PathVariable Long id) {
        return preferenceMapper.map(preferenceService.updateById(preferenceMapper.map(preferenceDto), id));
    }


    @GetMapping("/all")
    public Page<PreferenceDto> getPreferencePage(@RequestParam int page, @RequestParam int size) {
        Page<Preference> preferencePage = preferenceService.getPage(page, size);
        return preferencePage.map(preferenceMapper::map);
    }


}
