package com.parc.api.service;

import com.parc.api.model.dto.ImageDto;
import com.parc.api.model.entity.Image;
import com.parc.api.model.mapper.ImageMapper;
import com.parc.api.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private ImageRepository imageRepository;


    public ResponseEntity<List<ImageDto>> getAllImages() {
        List<Image> imageList = imageRepository.findAll();
        List<ImageDto> imageDtoList = imageList.stream()
                .map(ImageMapper::toDto).toList();
        return ResponseEntity.ok(imageDtoList);
    }


    public ResponseEntity<ImageDto> getImageById(@PathVariable int id) {
        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            ImageDto imageDto = ImageMapper.toDto(imageOptional.get());
            return ResponseEntity.ok(imageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<ImageDto> createImage(@RequestBody ImageDto imageDto, @PathVariable("idTypeImage") int idTypeImage, @PathVariable("idParc") int idParc) throws Exception {
        if (imageDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Image image = ImageMapper.toEntity(imageDto);
        Image savedImage = imageRepository.save(image);
        ImageDto savedImageDto = ImageMapper.toDto(savedImage);
        return new ResponseEntity<>(savedImageDto, HttpStatus.CREATED);
    }


    public ResponseEntity<ImageDto> updateImage(@PathVariable int id, @RequestBody ImageDto imageDto) {
        Optional<Image> foundImageOptional = imageRepository.findById(id);
        if (foundImageOptional.isPresent()) {
            Image foundImage = foundImageOptional.get();
            foundImage.setRefImage(imageDto.getRefImage());
            Image savedImage = imageRepository.save(foundImage);
            ImageDto updatedImageDto = ImageMapper.toDto(savedImage);
            return ResponseEntity.ok(updatedImageDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    public ResponseEntity<ImageDto> deleteImage(@PathVariable int id) {
        Optional<Image> ImageOptional = imageRepository.findById(id);
        if (ImageOptional.isPresent()) {
            imageRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
