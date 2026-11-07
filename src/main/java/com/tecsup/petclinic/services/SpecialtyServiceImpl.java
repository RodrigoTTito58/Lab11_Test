package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        Specialty specialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));
        return specialtyMapper.mapToDto(specialty);
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) throws SpecialtyNotFoundException {
        findById(specialtyDTO.getId());
        Specialty specialty = specialtyRepository.save(specialtyMapper.mapToEntity(specialtyDTO));
        return specialtyMapper.mapToDto(specialty);
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty with id " + id + " was not found"));
        specialtyRepository.delete(specialty);
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty with id " + id + " was not found"));
        return specialtyMapper.mapToDto(specialty);
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyRepository.findAll()
                .stream()
                .map(specialtyMapper::mapToDto)
                .toList();
    }
}
