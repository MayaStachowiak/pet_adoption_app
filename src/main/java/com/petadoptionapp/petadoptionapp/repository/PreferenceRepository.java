package com.petadoptionapp.petadoptionapp.repository;

import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
}
