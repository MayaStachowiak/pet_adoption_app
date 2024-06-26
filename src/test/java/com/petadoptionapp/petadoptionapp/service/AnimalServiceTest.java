package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.repository.AnimalRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AnimalServiceTest {

    private AnimalRepository animalRepository;
    private AnimalService animalService;
    private Animal animal;

    @BeforeEach
    public void setUp() {
        animalRepository = mock(AnimalRepository.class);
        animalService = new AnimalService(animalRepository);
        animal = new Animal();
        animal.setName("Reksio");
    }

    @Test
    public void testSaveAnimal() {
        when(animalService.save(any(Animal.class))).thenReturn(animal);

        Animal saveAnimal = animalService.save(animal);

        assertEquals("Reksio", saveAnimal.getName());
        verify(animalRepository, times(1)).save(animal);
    }

    @Test
    public void testGetAnimalById() {
        when(animalRepository.findById(1l)).thenReturn(Optional.ofNullable(animal));

        Animal resultAnimal = animalService.getById(1l);

        assertEquals(animal, resultAnimal);
    }

    @Test
    public void testGetAnimalById_NotFound() {
        when(animalRepository.findById(1l)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> animalService.getById(1l));
    }


    @Test
    public void testDeleteAnimalById() {
        animalService.deleteById(1l);

        verify(animalRepository, times(1)).deleteById(1l);
    }

    @Test
    public void testUpdateAnimalById() {
        when(animalRepository.findById(1l)).thenReturn(Optional.of(animal));

        Animal animalUpdate = new Animal();
        animalUpdate.setName("Burek");

        assertEquals("Reksio", animal.getName());

        animalService.updateById(animalUpdate, 1l);

        assertEquals("Burek", animal.getName());
    }


    @Test
    public void testGetPageAnimal() {
        Animal animal2 = new Animal();
        animal2.setName("Sarunia Mała Księżniczka");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Animal> animalPage = new PageImpl<>(Arrays.asList(animal, animal2), pageRequest, 2);

        when(animalRepository.findAll(pageRequest)).thenReturn(animalPage);

        Page<Animal> page = animalService.getPage(0, 10);

        assertFalse(page.isEmpty());
        assertEquals(2, page.getTotalElements());
        assertEquals(animal.getName(), page.getContent().get(0).getName());
        assertEquals(animal2.getName(), page.getContent().get(1).getName());

    }


}
