package com.petadoptionapp.petadoptionapp.bean.dao;


import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import com.petadoptionapp.petadoptionapp.bean.dao.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   // private User user;
   // private Animal animal;
    private String message;


}
