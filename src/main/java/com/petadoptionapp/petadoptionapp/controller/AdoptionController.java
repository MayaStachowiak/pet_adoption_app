package com.petadoptionapp.petadoptionapp.controller;


import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import com.petadoptionapp.petadoptionapp.mapper.AdoptionMapper;
import com.petadoptionapp.petadoptionapp.service.AdoptionService;
import lombok.RequiredArgsConstructor;
import org.openapitools.model.AdoptionDto;
import org.openapitools.model.AdoptionsPost200ResponseDto;
import org.openapitools.model.AnimalsIdGet404ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adoptions")
@RequiredArgsConstructor
public class AdoptionController {

    private final AdoptionService adoptionService;
    private final AdoptionMapper adoptionMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdoptionsPost200ResponseDto> saveAdoption(@RequestBody AdoptionDto adoptionDto) {
        Adoption adoptionToSave = adoptionService.save(adoptionMapper.map(adoptionDto));
        AdoptionDto adoptionToSaveDto = adoptionMapper.map(adoptionToSave);

        AdoptionsPost200ResponseDto response = new AdoptionsPost200ResponseDto().adoption(adoptionToSaveDto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getAdoptionById(@PathVariable Long id) {
        AdoptionDto adoptionDto = adoptionMapper.map(adoptionService.getById(id));
        if (adoptionDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AnimalsIdGet404ResponseDto());
        }
        return ResponseEntity.ok(adoptionDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAdoptionById(@PathVariable Long id) {
        adoptionService.deleteById(id);
    }

    @PatchMapping("/{id}")
    public AdoptionDto updateAnimalById(@RequestBody AdoptionDto adoptionDto, @PathVariable Long id) {
        return adoptionMapper.map(adoptionService.updateById(adoptionMapper.map(adoptionDto), id));
    }


    @GetMapping("/all")
    public Page<AdoptionDto> getAdoptionPage(@RequestParam int page, @RequestParam int size) {
        Page<Adoption> adoptionPage = adoptionService.getPage(page, size);
        return adoptionPage.map(adoptionMapper::map);

    }

}
