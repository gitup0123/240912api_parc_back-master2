package com.parc.api.model.dto;





import com.parc.api.model.entity.EnregistrerId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnregistrerDto {

    private EnregistrerId idEnregistrer;
    private LocalDate dateEnregistrer;
    private ParcDto idParc;
    private UtilisateurDto idUtilisateur;

}
