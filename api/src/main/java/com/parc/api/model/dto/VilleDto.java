package com.parc.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VilleDto {
    private int idVille;
    private String nomVille;
    private String codePostal;
    private RegionDto idRegion;
}
