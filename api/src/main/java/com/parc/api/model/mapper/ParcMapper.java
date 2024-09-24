package com.parc.api.model.mapper;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.dto.ParkingDto;
import com.parc.api.model.dto.VilleDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.entity.Parking;
import com.parc.api.model.entity.Ville;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public class ParcMapper {

    public static ParcDto toDto(Parc parc) {

        ParkingDto parkingDto = ParkingMapper.toDto(parc.getIdParking());
        VilleDto villeDto = VilleMapper.toDto(parc.getIdVille());

        ParcDto parcDto = new ParcDto();
        parcDto.setId(parc.getId());
        parcDto.setNomParc(parc.getNomParc());
        parcDto.setPresentation(parc.getPresentation());
        parcDto.setAdresse(parc.getAdresse());
        parcDto.setLatitudeParc(parc.getLatitudeParc());
        parcDto.setLongitudeParc(parc.getLongitudeParc());
        parcDto.setSiteInternet(parc.getSiteInternet());
        parcDto.setNumeroTelParc(parc.getNumeroTelParc());
        parcDto.setIsRestauration(parc.getIsRestauration());
        parcDto.setIsBoutique(parc.getIsBoutique());
        parcDto.setIsSejour(parc.getIsSejour());
        parcDto.setIsTransportCommun(parc.getIsTransportCommun());
        parcDto.setUrlAffilation(parc.getUrlAffilation());
        parcDto.setIdParking(parkingDto);
        parcDto.setIdVille(villeDto);
        return parcDto;
    }

    public static Parc toEntity(ParcDto parcDto) {

        Parking parking = ParkingMapper.toEntity(parcDto.getIdParking());
        Ville ville = VilleMapper.toEntity(parcDto.getIdVille());

        Parc parc = new Parc();
        parc.setId(parcDto.getId());
        parc.setNomParc(parcDto.getNomParc());
        parc.setPresentation(parcDto.getPresentation());
        parc.setAdresse(parcDto.getAdresse());
        parc.setLatitudeParc(parcDto.getLatitudeParc());
        parc.setLongitudeParc(parcDto.getLongitudeParc());
        parc.setSiteInternet(parcDto.getSiteInternet());
        parc.setNumeroTelParc(parcDto.getNumeroTelParc());
        parc.setIsRestauration(parcDto.getIsRestauration());
        parc.setIsBoutique(parcDto.getIsBoutique());
        parc.setIsSejour(parcDto.getIsSejour());
        parc.setIsTransportCommun(parcDto.getIsTransportCommun());
        parc.setUrlAffilation(parcDto.getUrlAffilation());
        parc.setIdParking(parking);
        parc.setIdVille(ville);
        return parc;
    }
}

