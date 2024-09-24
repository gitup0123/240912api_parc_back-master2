package com.parc.api.model.dto;

import com.parc.api.model.entity.PossederId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PossederDto {

    private PossederId idPosseder;
    private String urlReseauSociaux;
    private ParcDto idParc;
    private ReseauxSociauxDto idReseauxSociaux;

}
