package com.petadoptionapp.petadoptionapp.bean.dao;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate adoptionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "animal_id", nullable = false)
    private Animal animal;

    @Override
    public String toString() {
        return "Adoption{" +
                "id=" + id +
                ", adoptionDate=" + adoptionDate +
                ", animalId=" + (animal != null ? animal.getId() : null) +
                '}';
    }

}
