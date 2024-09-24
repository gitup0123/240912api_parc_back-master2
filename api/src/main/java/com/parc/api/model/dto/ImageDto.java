package com.parc.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Integer idImage;
    private String refImage;
    private ParcDto idParc;
    private TypeImageDto idTypeImage;

}
