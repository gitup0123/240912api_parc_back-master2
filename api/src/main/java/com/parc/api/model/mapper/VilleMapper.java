package com.parc.api.model.mapper;

import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.dto.VilleDto;
import com.parc.api.model.entity.Region;
import com.parc.api.model.entity.Ville;

public class VilleMapper {

    public static VilleDto toDto(Ville ville) {

        RegionDto regionDto = RegionMapper.toDto(ville.getIdRegion());

        VilleDto villeDto = new VilleDto();
        villeDto.setIdVille(ville.getId());
        villeDto.setNomVille(ville.getNomVille());
        villeDto.setCodePostal(ville.getCodePostal());
        villeDto.setIdRegion(regionDto);
        return villeDto;
    }
    public static Ville toEntity(VilleDto villeDto) {

        Region region = RegionMapper.toEntity(villeDto.getIdRegion());

        Ville ville = new Ville();
        ville.setId(villeDto.getIdVille());
        ville.setNomVille(villeDto.getNomVille());
        ville.setCodePostal(villeDto.getCodePostal());
        ville.setIdRegion(region);
        return ville;
    }
}
