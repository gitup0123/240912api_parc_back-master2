package com.parc.api.service;

import com.parc.api.model.dto.ClasserDto;
import com.parc.api.model.entity.Classer;
import com.parc.api.model.mapper.ClasserMapper;
import com.parc.api.repository.ClasserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClasserService {

    private final ClasserRepository classerRepository;

    public List<ClasserDto> getAllClassers() {
        List<Classer> classerList = classerRepository.findAll();
        return classerList.stream()
                .map(ClasserMapper::toDto).toList();
    }

    public Optional<ClasserDto> getClasserById(Integer id) {
        Optional<Classer> classer = classerRepository.findById(id);
        return classer.map(ClasserMapper::toDto);
    }

    public ClasserDto createClasser(ClasserDto classerDto) {
        Classer classer = ClasserMapper.toEntity(classerDto);
        Classer savedClasser = classerRepository.save(classer);
        return ClasserMapper.toDto(savedClasser);
    }

    public boolean deleteClasser(Integer id) {
        Optional<Classer> classerOptional = classerRepository.findById(id);
        if (classerOptional.isPresent()) {
            classerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<ClasserDto> updateClasser(Integer id, ClasserDto classerDto) {
        Optional<Classer> existingClasser = classerRepository.findById(id);
        if (existingClasser.isPresent()) {
            Classer classer = existingClasser.get();
            classer = classerRepository.save(classer);
            return Optional.of(ClasserMapper.toDto(classer));
        }
        return Optional.empty();
    }
}
