package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {

    private AnimalRepository animalRepository;

    public Animal save(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal getById(Long id) {
        return  animalRepository.findById(id).orElseThrow( () -> new EntityNotFoundException(id.toString()));
    }

    public void deleteById(Long id) {
        animalRepository.deleteById(id);
    }

    public Page<Animal> getPage(int page, int size) {
        return  animalRepository.findAll(PageRequest.of(page, size));
    }

    public Animal updateById(Animal animal, Long id) {
        Animal animalDb = getById(id);
        animalDb.setType(animal.getType());
        animalDb.setName(animal.getName());
        animalDb.setAge(animal.getAge());
        animalDb.setColor(animal.getColor());
        animalDb.setStatus(animal.getStatus());
        animalDb.setShortDescription(animal.getShortDescription());
        animalDb.setAdoptions(animal.getAdoptions());
        animalDb.setPreferences(animal.getPreferences());
        return animalDb;
    }


}
