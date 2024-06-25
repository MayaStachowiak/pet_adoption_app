package com.petadoptionapp.petadoptionapp.repository;

import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {


}
