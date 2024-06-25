package com.petadoptionapp.petadoptionapp.mapper;


import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import com.petadoptionapp.petadoptionapp.bean.dto.AdoptionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdoptionMapper {

    AdoptionDto map(Adoption adoption);
    Adoption map(AdoptionDto adoptionDto);
}
