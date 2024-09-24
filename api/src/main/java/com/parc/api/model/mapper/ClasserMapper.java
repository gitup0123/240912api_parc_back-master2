package com.parc.api.model.mapper;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.TypeParcDto;
import com.parc.api.model.entity.Classer;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.TypeParc;

public class ClasserMapper {

    public static ClasserDto toDto(Classer classer) {

        ParcDto parcDto = ParcMapper.toDto(classer.getIdParc());
        TypeParcDto typeParcDto = TypeParcMapper.toDto(classer.getIdTypeParc());

        ClasserDto classerDto = new ClasserDto();
        classerDto.setIdClasser(classer.getId());
        classerDto.setIdParc(parcDto);
        classerDto.setIdTypeParc(typeParcDto);
        return classerDto;
    }
    public static Classer toEntity(ClasserDto classerDto) {

        Parc parc = ParcMapper.toEntity(classerDto.getIdParc());
        TypeParc type = TypeParcMapper.toEntity(classerDto.getIdTypeParc());

        Classer classer = new Classer();
        classer.setId(classer.getId());
        classer.setIdParc(parc);
        classer.setIdTypeParc(type);
        return classer;
    }
}

