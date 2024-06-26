package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import com.petadoptionapp.petadoptionapp.repository.PreferenceRepository;
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

public class PreferenceServiceTest {

    private PreferenceRepository preferenceRepository;
    private PreferenceService preferenceService;
    private Preference preference;

    @BeforeEach
    public void setUp() {
        preferenceRepository = mock(PreferenceRepository.class);
        preferenceService = new PreferenceService(preferenceRepository);
        preference = new Preference();
        preference.setType("Cat");
    }

    @Test
    public void testSavePreference() {
        when(preferenceService.save(any(Preference.class))).thenReturn(preference);

        Preference savedPreference = preferenceService.save(preference);

        assertEquals("Cat", savedPreference.getType());
        verify(preferenceRepository, times(1)).save(preference);
    }

    @Test
    public void testGetPreferenceById() {
        when(preferenceRepository.findById(1L)).thenReturn(Optional.of(preference));

        Preference resultPreference = preferenceService.getById(1L);

        assertEquals(preference, resultPreference);
    }


    @Test
    public void testGetPreferenceById_NotFound() {
        when(preferenceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> preferenceService.getById(1L));
    }


    @Test
    public void testDeletePreferenceById() {
        preferenceService.deleteById(1L);

        verify(preferenceRepository, times(1)).deleteById(1L);
    }


    @Test
    public void testUpdatePreferenceById() {
        when(preferenceRepository.findById(1L)).thenReturn(Optional.of(preference));
        Preference preferenceUpdate = new Preference();
        preferenceUpdate.setType("Mooore cats");

        assertEquals("Cat", preference.getType());

        preferenceService.updateById(preferenceUpdate, 1L);

        assertEquals("Mooore cats", preference.getType());
    }

    @Test
    public void testGetPagePreference() {
        Preference preference2 = new Preference();
        preference2.setType("Ambystoma mexicanum");

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Preference> preferencePage = new PageImpl<>(Arrays.asList(preference, preference2), pageRequest, 2);

        when(preferenceRepository.findAll(pageRequest)).thenReturn(preferencePage);

        Page<Preference> page = preferenceService.getPage(0, 10);

        assertFalse(page.isEmpty());
        assertEquals(2, page.getTotalElements());
        assertEquals(preference.getType(), page.getContent().get(0).getType());
        assertEquals(preference2.getType(), page.getContent().get(1).getType());
    }


}
