package com.parc.api.service;

import com.parc.api.model.dto.PossederDto;
import com.parc.api.model.entity.Posseder;
import com.parc.api.model.mapper.PossederMapper;
import com.parc.api.repository.PossederRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PossederService {
    private final PossederRepository possederRepository;

    public ResponseEntity<List<PossederDto>> findAll() {
        List<Posseder> posseders = possederRepository.findAll();
        List<PossederDto> possederDtos = posseders.stream()
                .map(PossederMapper::toDto).toList();
        return ResponseEntity.ok(possederDtos);
    }


    public ResponseEntity<PossederDto> getPossederById(@PathVariable Integer id) {
        Optional<Posseder> posseder = possederRepository.findById(id);
        if (posseder.isPresent()) {
            PossederDto possederDto = PossederMapper.toDto(posseder.get());
            return ResponseEntity.ok(possederDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<PossederDto> createPosseder(@RequestBody PossederDto possederDto) {
        if (possederDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Posseder posseder = PossederMapper.toEntity(possederDto);
        Posseder savedPosseder = possederRepository.save(posseder);
        PossederDto savedPossederDto = PossederMapper.toDto(savedPosseder);
        return new ResponseEntity<>(savedPossederDto, HttpStatus.CREATED);
    }


    public ResponseEntity<Void> deletePosseder(@PathVariable Integer id) {
        Optional<Posseder> possederOptional = possederRepository.findById(id);
        if (possederOptional.isPresent()) {
            possederRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<PossederDto> updatePosseder(@PathVariable Integer id, @RequestBody PossederDto possederDto) {
        Optional<Posseder> existingPosseder = possederRepository.findById(id);
        if (existingPosseder.isPresent()) {
            Posseder posseder = existingPosseder.get();
            posseder.setUrlReseauSociaux(possederDto.getUrlReseauSociaux());
            posseder = possederRepository.save(posseder);
            return ResponseEntity.ok(PossederMapper.toDto(posseder));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
