package com.petadoptionapp.petadoptionapp.controller;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.mapper.AnimalMapper;
import com.petadoptionapp.petadoptionapp.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AnimalDto;
import org.openapitools.model.AnimalsIdGet404ResponseDto;
import org.openapitools.model.AnimalsPost200ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/animals")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final AnimalMapper animalMapper;

    @PostMapping
    public ResponseEntity<AnimalsPost200ResponseDto> saveAnimal(@RequestBody AnimalDto animalDto) {
        Animal animalToSave = animalService.save(animalMapper.map(animalDto));
        AnimalDto animalToSaveDto = animalMapper.map(animalToSave);
        AnimalsPost200ResponseDto response = new AnimalsPost200ResponseDto().animal(animalToSaveDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAnimalById(@PathVariable Long id) {
        AnimalDto animalDto = animalMapper.map(animalService.getById(id));
        if (animalDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AnimalsIdGet404ResponseDto());
        }
        return ResponseEntity.ok(animalDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAnimalById(@PathVariable Long id) {
        animalService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public AnimalDto updateAnimalById(@RequestBody AnimalDto animalDto, @PathVariable Long id) {
        return animalMapper.map(animalService.updateById(animalMapper.map(animalDto), id));
    }

    @GetMapping("/all")
    public Page<AnimalDto> getAnimalPage(@RequestParam int page, @RequestParam int size) {
        Page<Animal> animalPage = animalService.getPage(page, size);
        return animalPage.map(animalMapper::map);
    }

}
