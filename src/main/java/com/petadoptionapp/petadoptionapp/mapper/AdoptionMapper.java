package com.petadoptionapp.petadoptionapp.mapper;


import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.model.AdoptionDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdoptionMapper {

    AdoptionDto map(Adoption adoption);
    Adoption map(AdoptionDto adoptionDto);
}
