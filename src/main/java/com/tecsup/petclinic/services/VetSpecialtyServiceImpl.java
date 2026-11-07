package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.entities.VetSpecialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import com.tecsup.petclinic.mappers.SpecialtyMapper;
import com.tecsup.petclinic.mappers.VetMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import com.tecsup.petclinic.repositories.VetRepository;
import com.tecsup.petclinic.repositories.VetSpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VetSpecialtyServiceImpl implements VetSpecialtyService {

    private final VetSpecialtyRepository vetSpecialtyRepository;
    private final VetRepository vetRepository;
    private final SpecialtyRepository specialtyRepository;
    private final VetMapper vetMapper;
    private final SpecialtyMapper specialtyMapper;

    @Override
    public VetSpecialtyDTO assign(VetSpecialtyDTO dto)
            throws VetNotFoundException, SpecialtyNotFoundException {
        if (!vetRepository.existsById(dto.getVetId())) {
            throw new VetNotFoundException("Vet with id " + dto.getVetId() + " was not found");
        }
        if (!specialtyRepository.existsById(dto.getSpecialtyId())) {
            throw new SpecialtyNotFoundException("Specialty with id " + dto.getSpecialtyId() + " was not found");
        }

        VetSpecialty relationship = new VetSpecialty(
                dto.getVetId(),
                dto.getSpecialtyId(),
                dto.getCertificationDate(),
                dto.getYearsExperience(),
                dto.isPrimary(),
                dto.getNotes());
        return toDto(vetSpecialtyRepository.save(relationship));
    }

    @Override
    public void remove(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException {
        VetSpecialty relationship = vetSpecialtyRepository.findById(
                        new com.tecsup.petclinic.entities.VetSpecialtyId(vetId, specialtyId))
                .orElseThrow(() -> new VetSpecialtyNotFoundException(
                        "Vet-specialty relationship was not found"));
        vetSpecialtyRepository.delete(relationship);
    }

    @Override
    public List<SpecialtyDTO> findSpecialtiesByVet(Integer vetId)
            throws VetNotFoundException, SpecialtyNotFoundException {
        if (!vetRepository.existsById(vetId)) {
            throw new VetNotFoundException("Vet with id " + vetId + " was not found");
        }
        List<SpecialtyDTO> specialties = new ArrayList<>();
        for (VetSpecialty relationship : vetSpecialtyRepository.findByVetId(vetId)) {
            Specialty specialty = specialtyRepository.findById(relationship.getSpecialtyId())
                    .orElseThrow(() -> new SpecialtyNotFoundException(
                            "Specialty with id " + relationship.getSpecialtyId() + " was not found"));
            specialties.add(specialtyMapper.mapToDto(specialty));
        }
        return specialties;
    }

    @Override
    public List<VetDTO> findVetsBySpecialty(Integer specialtyId)
            throws SpecialtyNotFoundException, VetNotFoundException {
        if (!specialtyRepository.existsById(specialtyId)) {
            throw new SpecialtyNotFoundException("Specialty with id " + specialtyId + " was not found");
        }
        List<VetDTO> vets = new ArrayList<>();
        for (VetSpecialty relationship : vetSpecialtyRepository.findBySpecialtyId(specialtyId)) {
            Vet vet = vetRepository.findById(relationship.getVetId())
                    .orElseThrow(() -> new VetNotFoundException(
                            "Vet with id " + relationship.getVetId() + " was not found"));
            vets.add(vetMapper.mapToDto(vet));
        }
        return vets;
    }

    private VetSpecialtyDTO toDto(VetSpecialty relationship) {
        return VetSpecialtyDTO.builder()
                .vetId(relationship.getVetId())
                .specialtyId(relationship.getSpecialtyId())
                .certificationDate(relationship.getCertificationDate())
                .yearsExperience(relationship.getYearsExperience())
                .primary(relationship.isPrimary())
                .notes(relationship.getNotes())
                .build();
    }
}
