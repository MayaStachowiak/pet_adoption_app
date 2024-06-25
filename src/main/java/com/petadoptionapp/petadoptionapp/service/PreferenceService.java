package com.petadoptionapp.petadoptionapp.service;

import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import com.petadoptionapp.petadoptionapp.repository.PreferenceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository preferenceRepository;

    public Preference save(Preference preference) {
        return preferenceRepository.save(preference);
    }

    public Preference getById(Long id) {
        return preferenceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
    }

    public void deleteById(Long id) {
        preferenceRepository.deleteById(id);
    }

    public Page<Preference> getPage(int page, int size) {
        return preferenceRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Preference updateById(Preference preference, Long id) {
        Preference preferenceDb = getById(id);
        preferenceDb.setType(preference.getType());
        preferenceDb.setColor(preference.getColor());
        preferenceDb.setMinAge(preference.getMinAge());
        preferenceDb.setMaxAge(preference.getMaxAge());
        preferenceDb.setUsers(preference.getUsers());
        preferenceDb.setAnimals(preference.getAnimals());
        return preferenceDb;
    }
}
