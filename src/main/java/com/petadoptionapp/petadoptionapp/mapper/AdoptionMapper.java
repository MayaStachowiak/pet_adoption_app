package com.petadoptionapp.petadoptionapp.mapper;


import com.petadoptionapp.petadoptionapp.bean.dao.Adoption;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.openapitools.model.AdoptionDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdoptionMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "animal", source = "animal")
    AdoptionDto map(Adoption adoption);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "animal", source = "animal")
    Adoption map(AdoptionDto adoptionDto);
}
