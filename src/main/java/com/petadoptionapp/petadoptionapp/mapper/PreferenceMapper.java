package com.petadoptionapp.petadoptionapp.mapper;

import com.petadoptionapp.petadoptionapp.bean.dao.Preference;
import com.petadoptionapp.petadoptionapp.bean.dto.PreferenceDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PreferenceMapper {

    PreferenceDto map(Preference preference);

    Preference map(PreferenceDto preferenceDto);
}
