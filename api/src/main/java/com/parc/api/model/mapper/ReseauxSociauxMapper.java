package com.parc.api.model.mapper;

import com.parc.api.model.dto.ReseauxSociauxDto;
import com.parc.api.model.entity.ReseauxSociaux;

public class ReseauxSociauxMapper {
    public static ReseauxSociauxDto toDto(ReseauxSociaux reseauxSociaux) {
        ReseauxSociauxDto reseauxSociauxDto = new ReseauxSociauxDto();
        reseauxSociauxDto.setId(reseauxSociaux.getId());
        reseauxSociauxDto.setLibReseauxSociaux(reseauxSociaux.getLibReseauSociaux());
        return reseauxSociauxDto;
    }

    public static ReseauxSociaux toEntity(ReseauxSociauxDto reseauxSociauxDto) {
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux();
        reseauxSociaux.setId(reseauxSociauxDto.getId());
        reseauxSociaux.setLibReseauSociaux(reseauxSociauxDto.getLibReseauxSociaux());
        return reseauxSociaux;
    }
}
