package com.parc.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcDto {
    private Integer id;
    private String nomParc;
    private String presentation;
    private String adresse;
    private Double longitudeParc;
    private Double latitudeParc;
    private String siteInternet;
    private String numeroTelParc;
    private Boolean isRestauration = false;
    private Boolean isBoutique = false;
    private Boolean isSejour = false;
    private Boolean isTransportCommun = false;
    private String urlAffilation;
    private ParkingDto idParking;
    private VilleDto idVille;
    private PeriodeDto idPeriode;
}
