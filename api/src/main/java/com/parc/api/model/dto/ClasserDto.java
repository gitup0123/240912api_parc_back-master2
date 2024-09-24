package com.parc.api.model.dto;



import com.parc.api.model.entity.ClasserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClasserDto {

    private ClasserId idClasser;
    private ParcDto idParc;
    private TypeParcDto idTypeParc;

}
