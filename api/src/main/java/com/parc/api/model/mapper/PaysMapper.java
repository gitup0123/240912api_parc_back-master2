package com.parc.api.model.mapper;

import com.parc.api.model.entity.Pays;
import com.parc.api.model.dto.PaysDto;

public class PaysMapper {

    public static PaysDto toDto(Pays pays) {
        PaysDto payDto = new PaysDto();
        payDto.setIdPays(pays.getId());
        payDto.setNomPays(pays.getNomPays());
        return payDto;
    }
    public static Pays toEntity(PaysDto payDto) {
        Pays pays = new Pays();
        pays.setId(payDto.getIdPays());
        pays.setNomPays(payDto.getNomPays());
        return pays;
    }
}
