package com.parc.api.service;

import com.parc.api.model.dto.ParcDto;
import com.parc.api.model.entity.Parc;
import com.parc.api.model.mapper.ParcMapper;
import com.parc.api.repository.ParcRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@AllArgsConstructor
public class ParcService {
    private final ParcRepository parcRepository;


    public ResponseEntity<List<ParcDto>> getAllParc() {
        List<Parc> parcList = parcRepository.findAll();
        List<ParcDto> parcDtoList = parcList.stream()
                .map(ParcMapper::toDto).toList();
        return ResponseEntity.ok(parcDtoList);
    }


    public ResponseEntity<ParcDto> getNomParc(@PathVariable String nomParc) {
        return parcRepository.findParcByNomParc(nomParc)
                .map(parc -> ResponseEntity.ok(ParcMapper.toDto(parc)))
                .orElse(ResponseEntity.notFound().build());
    }


    public ResponseEntity<ParcDto> createParc(@RequestBody ParcDto parcDto) {
        if (parcDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Parc parc = ParcMapper.toEntity(parcDto);
        Parc savedParc = parcRepository.save(parc);
        ParcDto savedParcDto = ParcMapper.toDto(savedParc);
        return new ResponseEntity<>(savedParcDto, HttpStatus.CREATED);
    }


    public ResponseEntity<Void> deleteParc(@PathVariable int id) {
        if (!parcRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        parcRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<ParcDto> updateParc(@PathVariable int id, @RequestBody ParcDto parcDto) {
        if (!parcRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Parc parc = ParcMapper.toEntity(parcDto);
        parc.setId(id);
        Parc updatedParc = parcRepository.save(parc);
        ParcDto updatedParcDto = ParcMapper.toDto(updatedParc);
        return ResponseEntity.ok(updatedParcDto);
    }
}
