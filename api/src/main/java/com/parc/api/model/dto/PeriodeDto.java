package com.parc.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PeriodeDto {
    private Integer idPeriode;
    private LocalDate dateOuverturePeriode;
    private LocalDate dateFermeturePeriode;
    private LocalTime heureOuverturePeriode;
    private LocalTime heureFermeturePeriode;
    private BigDecimal prixAdultePeriode;
    private BigDecimal prixEnfantPeriode;
    private ParcDto idParc;

}
