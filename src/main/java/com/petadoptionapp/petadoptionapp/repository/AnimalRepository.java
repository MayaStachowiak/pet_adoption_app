package com.petadoptionapp.petadoptionapp.repository;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

}
