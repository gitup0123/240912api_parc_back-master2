package com.parc.api.service;

import com.parc.api.model.dto.RegionDto;
import com.parc.api.model.entity.Region;
import com.parc.api.model.mapper.RegionMapper;
import com.parc.api.repository.PaysRepository;
import com.parc.api.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;


    public ResponseEntity<List<RegionDto>> getAllRegions() {
        List<Region> regions = regionRepository.findAll();
        List<RegionDto> regionDto = regions.stream()
                .map(RegionMapper::toDto).toList();
        return ResponseEntity.ok(regionDto);
    }

    public ResponseEntity<RegionDto> getRegionById(@PathVariable Integer id) {
        Optional<Region> region = regionRepository.findById(id);
        if (region.isPresent()) {
            RegionDto regionDto = RegionMapper.toDto(region.get());
            return ResponseEntity.ok(regionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<RegionDto> createRegionByPays(@RequestBody RegionDto regionDto) {
        if (regionDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Region region = RegionMapper.toEntity(regionDto);
        Region savedRegion = regionRepository.save(region);
        RegionDto savedRegionDto = RegionMapper.toDto(savedRegion);
        return new ResponseEntity<>(savedRegionDto, HttpStatus.CREATED);
    }


    public ResponseEntity<Void> deleteRegion(@PathVariable Integer id) {
        Optional<Region> regionOptional = regionRepository.findById(id);
        if (regionOptional.isPresent()) {
            regionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<RegionDto> updateRegion(@PathVariable Integer id, @RequestBody RegionDto regionDto) {
        Optional<Region> foundRegionOptional = regionRepository.findById(id);
        if (foundRegionOptional.isPresent()) {
            Region foundRegion= foundRegionOptional.get();
            foundRegion.setNomRegion(regionDto.getNomRegion());
            Region savedRegion= regionRepository.save(foundRegion);
            RegionDto updatedRegionDto = RegionMapper.toDto(savedRegion);
            return ResponseEntity.ok(updatedRegionDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
