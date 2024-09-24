package com.parc.api.model.mapper;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.TypeImageDto;
import com.parc.api.model.entity.Image;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.TypeImage;

public class ImageMapper {

    public static ImageDto toDto(Image image) {

        ParcDto parcDto = ParcMapper.toDto(image.getIdParc());
        TypeImageDto typeImageDto = TypeImageMapper.toDto(image.getIdTypeImage());

        ImageDto imageDto = new ImageDto();
        imageDto.setIdImage(image.getId());
        imageDto.setRefImage(image.getRefImage());
        imageDto.setIdParc(parcDto);
        imageDto.setIdTypeImage(typeImageDto);
        return imageDto;
    }

    public static Image toEntity(ImageDto imageDto) {

        Parc parc = ParcMapper.toEntity(imageDto.getIdParc());
        TypeImage typeImage = TypeImageMapper.toEntity(imageDto.getIdTypeImage());

        Image image = new Image();
        image.setId(imageDto.getIdImage());
        image.setRefImage(imageDto.getRefImage());
        image.setIdTypeImage(typeImage);
        image.setIdParc(parc);
        image.setIdTypeImage(typeImage);
        return image;
    }
}
