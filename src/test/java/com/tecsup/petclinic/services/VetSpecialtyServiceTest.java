package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.dtos.VetSpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.exceptions.VetSpecialtyNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class VetSpecialtyServiceTest {

    @Autowired
    private VetSpecialtyService vetSpecialtyService;

    @Test
    void testAssignSpecialtyToVet() throws VetNotFoundException, SpecialtyNotFoundException {
        VetSpecialtyDTO assigned = vetSpecialtyService.assign(newRelationship());

        assertEquals(1, assigned.getVetId());
        assertEquals(3, assigned.getSpecialtyId());
        assertEquals(6, assigned.getYearsExperience());
        assertEquals(LocalDate.of(2020, 5, 10), assigned.getCertificationDate());
    }

    @Test
    void testFindSpecialtiesByVet() throws VetNotFoundException, SpecialtyNotFoundException {
        vetSpecialtyService.assign(newRelationship());

        List<SpecialtyDTO> specialties = vetSpecialtyService.findSpecialtiesByVet(1);

        assertTrue(specialties.stream().anyMatch(specialty -> specialty.getId().equals(3)));
    }

    @Test
    void testFindVetsBySpecialty() throws SpecialtyNotFoundException, VetNotFoundException {
        List<VetDTO> vets = vetSpecialtyService.findVetsBySpecialty(1);

        assertTrue(vets.stream().anyMatch(vet -> vet.getId().equals(2)));
    }

    @Test
    void testRemoveSpecialtyFromVet()
            throws VetNotFoundException, SpecialtyNotFoundException, VetSpecialtyNotFoundException {
        vetSpecialtyService.assign(newRelationship());

        vetSpecialtyService.remove(1, 3);

        assertThrows(VetSpecialtyNotFoundException.class, () -> vetSpecialtyService.remove(1, 3));
    }

    private VetSpecialtyDTO newRelationship() {
        return VetSpecialtyDTO.builder()
                .vetId(1)
                .specialtyId(3)
                .certificationDate(LocalDate.of(2020, 5, 10))
                .yearsExperience(6)
                .primary(false)
                .notes("Additional certification")
                .build();
    }
}
