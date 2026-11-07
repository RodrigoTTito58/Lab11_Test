package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.mappers.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner newOwner = ownerRepository.save(ownerMapper.mapToEntity(ownerDTO));
        return ownerMapper.mapToDto(newOwner);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) throws OwnerNotFoundException {
        findById(ownerDTO.getId());
        Owner updatedOwner = ownerRepository.save(ownerMapper.mapToEntity(ownerDTO));
        return ownerMapper.mapToDto(updatedOwner);
    }

    @Override
    public void delete(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with id " + id + " was not found"));
        ownerRepository.delete(owner);
    }

    @Override
    public OwnerDTO findById(Integer id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner with id " + id + " was not found"));
        return ownerMapper.mapToDto(owner);
    }

    @Override
    public List<OwnerDTO> findAll() {
        return ownerRepository.findAll()
                .stream()
                .map(ownerMapper::mapToDto)
                .toList();
    }
}
