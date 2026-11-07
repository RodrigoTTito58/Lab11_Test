package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet newVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(newVet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) throws VetNotFoundException {
        findById(vetDTO.getId());
        Vet updatedVet = vetRepository.save(vetMapper.mapToEntity(vetDTO));
        return vetMapper.mapToDto(updatedVet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet with id " + id + " was not found"));
        vetRepository.delete(vet);
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet with id " + id + " was not found"));
        return vetMapper.mapToDto(vet);
    }

    @Override
    public List<VetDTO> findAll() {
        return vetRepository.findAll()
                .stream()
                .map(vetMapper::mapToDto)
                .toList();
    }
}
