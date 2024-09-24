package com.parc.api.model.mapper;

import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.entity.Region;

public class RegionMapper {

    public static RegionDto toDto(Region region) {

        PaysDto paysDto = PaysMapper.toDto(region.getIdPays());

        RegionDto regionDto = new RegionDto();
        regionDto.setIdRegion(region.getId());
        regionDto.setNomRegion(region.getNomRegion());
        regionDto.setIdPays(paysDto);
        return regionDto;
    }

    public static Region toEntity(RegionDto regionDto) {

        Pays pays = PaysMapper.toEntity(regionDto.getIdPays());

        Region region = new Region();
        region.setId(regionDto.getIdRegion());
        region.setNomRegion(regionDto.getNomRegion());
        region.setIdPays(pays);
        return region;
    }
}