package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mappers.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit visit = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(visit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) throws VisitNotFoundException {
        findById(visitDTO.getId());
        Visit visit = visitRepository.save(visitMapper.mapToEntity(visitDTO));
        return visitMapper.mapToDto(visit);
    }

    @Override
    public void delete(Integer id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit with id " + id + " was not found"));
        visitRepository.delete(visit);
    }

    @Override
    public VisitDTO findById(Integer id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit with id " + id + " was not found"));
        return visitMapper.mapToDto(visit);
    }

    @Override
    public List<VisitDTO> findAll() {
        return visitRepository.findAll()
                .stream()
                .map(visitMapper::mapToDto)
                .toList();
    }
}
