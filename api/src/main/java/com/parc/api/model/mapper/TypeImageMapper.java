package com.parc.api.model.mapper;

import com.parc.api.model.dto.TypeImageDto;
import com.parc.api.model.entity.TypeImage;

public class TypeImageMapper {
    public static TypeImageDto toDto(TypeImage typeImage) {
        TypeImageDto typeImageDto = new TypeImageDto();
        typeImageDto.setIdImage(typeImage.getId());
        typeImageDto.setLibTypeImage(typeImage.getLibTypeImage());
        return typeImageDto;
    }
    public static TypeImage toEntity(TypeImageDto typeImageDto) {
        TypeImage typeImage = new TypeImage();
        typeImage.setId(typeImageDto.getIdImage());
        typeImage.setLibTypeImage(typeImageDto.getLibTypeImage());
        return typeImage;
    }
}
