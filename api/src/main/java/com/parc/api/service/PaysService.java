package com.parc.api.service;

import com.parc.api.model.dto.PaysDto;
import com.parc.api.model.entity.Pays;
import com.parc.api.model.mapper.PaysMapper;
import com.parc.api.repository.PaysRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaysService {
    private final PaysRepository paysRepository;


    public ResponseEntity<List<PaysDto>> getAllPays() {
        List<Pays> pays = paysRepository.findAll();
        List<PaysDto> paysDto = pays.stream()
                .map(PaysMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(paysDto);
    }

    public ResponseEntity<PaysDto> getPaysById(@PathVariable Integer id) {
        Optional<Pays> pays = paysRepository.findById(id);
        if (pays.isPresent()) {
            PaysDto paysDto = PaysMapper.toDto(pays.get());
            return ResponseEntity.ok(paysDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<PaysDto> createPay(@RequestBody PaysDto paysDto) {
        if (paysDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Pays pays = PaysMapper.toEntity(paysDto);
        Pays savedPays = paysRepository.save(pays);
        PaysDto savedPaysDto = PaysMapper.toDto(savedPays);
        return new ResponseEntity<>(savedPaysDto, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deletePay(@PathVariable Integer id) {
        Optional<Pays> paysOptional = paysRepository.findById(id);
        if (paysOptional.isPresent()) {
            paysRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<PaysDto> updatePays(@PathVariable Integer id, @RequestBody PaysDto paysDto) {
        Optional<Pays> foundPaysOptional = paysRepository.findById(id);
        if (foundPaysOptional.isPresent()) {
            Pays foundPays = foundPaysOptional.get();
            foundPays.setNomPays(paysDto.getNomPays());
            Pays savedPays = paysRepository.save(foundPays);
            PaysDto updatedPaysDto = PaysMapper.toDto(savedPays);
            return ResponseEntity.ok(updatedPaysDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
