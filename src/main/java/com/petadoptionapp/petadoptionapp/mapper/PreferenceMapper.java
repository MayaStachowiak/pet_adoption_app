package com.petadoptionapp.petadoptionapp.mapper;

import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.model.PreferenceDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PreferenceMapper {

    PreferenceDto map(Preference preference);

    Preference map(PreferenceDto preferenceDto);
}
