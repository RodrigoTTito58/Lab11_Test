package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;

import java.util.List;

public interface VetSpecialtyService {

    VetSpecialtyDTO assign(VetSpecialtyDTO vetSpecialtyDTO)
            throws VetNotFoundException, SpecialtyNotFoundException;

    void remove(Integer vetId, Integer specialtyId) throws VetSpecialtyNotFoundException;

    List<SpecialtyDTO> findSpecialtiesByVet(Integer vetId)
            throws VetNotFoundException, SpecialtyNotFoundException;

    List<VetDTO> findVetsBySpecialty(Integer specialtyId)
            throws SpecialtyNotFoundException, VetNotFoundException;
}
