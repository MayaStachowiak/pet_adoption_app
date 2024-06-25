package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import com.petadoptionapp.petadoptionapp.repository.AdoptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdoptionService {

    private final AdoptionRepository adoptionRepository;

    public Adoption save(Adoption adoption) {
        return adoptionRepository.save(adoption);
    }

    public Adoption getById(Long id) {
        return adoptionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public void deleteById(Long id) {
        adoptionRepository.deleteById(id);
    }

    public Page<Adoption> getPage(int page, int size) {
        return adoptionRepository.findAll(PageRequest.of(page, size));
    }

    public Adoption updateById(Adoption adoption, Long id) {
        Adoption adoptionDb = getById(id);
        adoptionDb.setUser(adoption.getUser());
        adoptionDb.setAnimal(adoption.getAnimal());
        adoptionDb.setAdoptionDate(adoption.getAdoptionDate());
        return adoptionDb;
    }

}
