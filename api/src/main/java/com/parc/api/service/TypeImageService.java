package com.parc.api.service;

import com.parc.api.model.dto.TypeImageDto;
import com.parc.api.model.entity.TypeImage;
import com.parc.api.model.mapper.TypeImageMapper;
import com.parc.api.repository.TypeImageRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeImageService {

    private final TypeImageRepository typeImageRepository;


    public ResponseEntity<List<TypeImageDto>> getAllTypeImage() {
        List<TypeImage> typeImage = typeImageRepository.findAll();
        List<TypeImageDto> typeImageDtos = typeImage.stream()
                .map(TypeImageMapper::toDto).toList();
        return ResponseEntity.ok(typeImageDtos);
    }


    public ResponseEntity<TypeImageDto> getTypeImageById(@PathVariable Integer id) {
        Optional<TypeImage> typeImage = typeImageRepository.findById(id);
        if (typeImage.isPresent()) {
            TypeImageDto typeImageDto = TypeImageMapper.toDto(typeImage.get());
            return ResponseEntity.ok(typeImageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<TypeImageDto> createTypeImage(@RequestBody TypeImageDto typeImageDto) {
        if (typeImageDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TypeImage typeImage = TypeImageMapper.toEntity(typeImageDto);
        TypeImage savedTypeImage = typeImageRepository.save(typeImage);
        TypeImageDto savedTypeImageDto = TypeImageMapper.toDto(savedTypeImage);
        return new ResponseEntity<>(savedTypeImageDto, HttpStatus.CREATED);
    }


    public ResponseEntity<Void> deleteTypeImage(@PathVariable Integer id) {
        Optional<TypeImage> typeImageOptional = typeImageRepository.findById(id);
        if (typeImageOptional.isPresent()) {
            typeImageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<TypeImageDto> updateTypeImage(@PathVariable Integer id, @RequestBody TypeImageDto typeImageDto) {
        Optional<TypeImage> foundTypeImageOptional = typeImageRepository.findById(id);
        if (foundTypeImageOptional.isPresent()) {
            TypeImage foundTypeImage = foundTypeImageOptional.get();
            foundTypeImage.setLibTypeImage(typeImageDto.getLibTypeImage());
            TypeImage savedTypeImage = typeImageRepository.save(foundTypeImage);
            TypeImageDto updatedTypeImageDto = TypeImageMapper.toDto(savedTypeImage);
            return ResponseEntity.ok(updatedTypeImageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
