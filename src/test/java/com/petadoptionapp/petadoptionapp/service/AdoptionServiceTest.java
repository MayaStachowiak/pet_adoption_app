package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import com.petadoptionapp.petadoptionapp.repository.AdoptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AdoptionServiceTest {

    private AdoptionRepository adoptionRepository;
    private AdoptionService adoptionService;
    private Adoption adoption;

    @BeforeEach
    public void setUp() {
        adoptionRepository = mock(AdoptionRepository.class);
        adoptionService = new AdoptionService(adoptionRepository);
        adoption = new Adoption();
        adoption.setAdoptionDate(LocalDate.now());
    }

    @Test
    public void testSaveAdoption() {
        when(adoptionService.save(any(Adoption.class))).thenReturn(adoption);

        Adoption savedAdoption = adoptionService.save(adoption);

        assertEquals(adoption.getAdoptionDate(), savedAdoption.getAdoptionDate());
        verify(adoptionRepository, times(1)).save(adoption);
    }


    @Test
    public void testGetAdoptionById() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(adoption));

        Adoption resultAdoption = adoptionService.getById(1L);

        assertEquals(adoption, resultAdoption);
    }


    @Test
    public void testGetAdoptionById_NotFound() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adoptionService.getById(1L));
    }

    @Test
    public void testDeleteAdoptionById() {
        adoptionService.deleteById(1L);

        verify(adoptionRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testUpdateAdoptionById() {
        when(adoptionRepository.findById(1L)).thenReturn(Optional.of(adoption));
        Adoption adoptionUpdate = new Adoption();
        adoptionUpdate.setAdoptionDate(LocalDate.of(2025, 1, 1));

        assertEquals(LocalDate.now(), adoption.getAdoptionDate());

        adoptionService.updateById(adoptionUpdate, 1L);

        assertEquals(LocalDate.of(2025, 1, 1), adoption.getAdoptionDate());
    }

    @Test
    public void testGetPageAdoption() {
        Adoption adoption2 = new Adoption();
        adoption2.setAdoptionDate(LocalDate.of(2025, 2, 1));

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Adoption> adoptionPage = new PageImpl<>(Arrays.asList(adoption, adoption2), pageRequest, 2);

        when(adoptionRepository.findAll(pageRequest)).thenReturn(adoptionPage);

        Page<Adoption> page = adoptionService.getPage(0, 10);

        assertFalse(page.isEmpty());
        assertEquals(2, page.getTotalElements());
        assertEquals(adoption.getAdoptionDate(), page.getContent().get(0).getAdoptionDate());
        assertEquals(adoption2.getAdoptionDate(), page.getContent().get(1).getAdoptionDate());
    }


}
