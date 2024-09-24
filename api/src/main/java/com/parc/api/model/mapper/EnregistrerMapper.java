package com.parc.api.model.mapper;




import com.parc.api.model.dto.EnregistrerDto;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.UtilisateurDto;
import com.parc.api.model.entity.Enregistrer;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Utilisateur;

public class EnregistrerMapper {

    public static EnregistrerDto toDto(Enregistrer enregistrer) {

        ParcDto parcDto = ParcMapper.toDto(enregistrer.getIdParc());
        UtilisateurDto utilisateurDto = UtilisateurMapper.toDto(enregistrer.getIdUtilisateur());

        EnregistrerDto enregistrerDto = new EnregistrerDto();
        enregistrerDto.setIdEnregistrer(enregistrer.getId());
        enregistrerDto.setDateEnregistrer(enregistrer.getDateEnregistrer());
        enregistrerDto.setIdParc(parcDto);
        enregistrerDto.setIdUtilisateur(utilisateurDto);
        return enregistrerDto;
    }

    public static Enregistrer toEntity(EnregistrerDto enregistrerDto) {

        Parc parc = ParcMapper.toEntity(enregistrerDto.getIdParc());
        Utilisateur utilisateur = UtilisateurMapper.toEntity(enregistrerDto.getIdUtilisateur());

        Enregistrer enregistrer = new Enregistrer();
        enregistrer.setId(enregistrerDto.getIdEnregistrer());
        enregistrer.setDateEnregistrer(enregistrerDto.getDateEnregistrer());
        enregistrer.setIdParc(parc);
        enregistrer.setIdUtilisateur(utilisateur);
        return enregistrer;
    }
}
