package com.parc.api.model.mapper;

import com.parc.api.model.dto.ParkingDto;
import com.parc.api.model.entity.Parking;

public class ParkingMapper {

    public static ParkingDto toDto(Parking parking) {
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setIdParking(parking.getId());
        parkingDto.setLibParking(parking.getLibParking());
        return parkingDto;
    }

    public static Parking toEntity(ParkingDto parkingDto) {
        Parking parking = new Parking();
        parking.setId(parkingDto.getIdParking());
        parking.setLibParking(parkingDto.getLibParking());
        return parking;
    }
}
