package com.petadoptionapp.petadoptionapp.repository;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query(value = "SELECT a.* FROM Animal a JOIN user_animal ua ON a.id = ua.animal_id WHERE ua.user_id = :userId", nativeQuery = true)
    Set<Animal> findFavoriteAnimalsByUserId(@Param("userId") Long userId);

}
