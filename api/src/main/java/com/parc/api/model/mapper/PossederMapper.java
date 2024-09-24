package com.parc.api.model.mapper;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.PossederDto;
import com.parc.api.model.dto.ReseauxSociauxDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Posseder;
import com.parc.api.model.entity.PossederId;
import com.parc.api.model.entity.ReseauxSociaux;

public class PossederMapper {

    public static PossederDto toDto(Posseder posseder) {
        ParcDto parcDto = ParcMapper.toDto(posseder.getIdParc());
        ReseauxSociauxDto reseauxSociauxDto = ReseauxSociauxMapper.toDto(posseder.getIdReseauSociaux());

        PossederDto possederDto = new PossederDto();
        possederDto.setIdPosseder(posseder.getId());
        possederDto.setUrlReseauSociaux(posseder.getUrlReseauSociaux());
        possederDto.setIdParc(parcDto);
        possederDto.setIdReseauxSociaux(reseauxSociauxDto);
        return possederDto;
    }

    public static Posseder toEntity(PossederDto possederDto) {
        Parc parc = ParcMapper.toEntity(possederDto.getIdParc());
        ReseauxSociaux reseauxSociaux = ReseauxSociauxMapper.toEntity(possederDto.getIdReseauxSociaux());

        PossederId possederId = new PossederId();
        possederId.setIdParc(parc.getId());
        possederId.setIdReseauSociaux(reseauxSociaux.getId());

        Posseder posseder = new Posseder();
        posseder.setId(possederId);
        posseder.setUrlReseauSociaux(possederDto.getUrlReseauSociaux());
        posseder.setIdParc(parc);
        posseder.setIdReseauSociaux(reseauxSociaux);
        return posseder;
    }
}
