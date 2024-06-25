package com.petadoptionapp.petadoptionapp.mapper;

import com.petadoptionapp.petadoptionapp.bean.dao.Animal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.model.AnimalDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalMapper {

    AnimalDto map(Animal animal);

    Animal map(AnimalDto animalDto);

}
