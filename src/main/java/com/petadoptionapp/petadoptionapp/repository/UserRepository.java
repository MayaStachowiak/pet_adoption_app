package com.petadoptionapp.petadoptionapp.repository;

import com.petadoptionapp.petadoptionapp.bean.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


}
