package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    void testCreateSpecialty() {
        SpecialtyDTO createdSpecialty = specialtyService.create(SpecialtyDTO.builder()
                .name("cardiology")
                .office("Lima")
                .openHour(8)
                .closeHour(17)
                .build());

        assertNotNull(createdSpecialty.getId());
        assertEquals("cardiology", createdSpecialty.getName());
        assertEquals("Lima", createdSpecialty.getOffice());
        assertEquals(8, createdSpecialty.getOpenHour());
        assertEquals(17, createdSpecialty.getCloseHour());
    }

    @Test
    void testUpdateSpecialty() throws SpecialtyNotFoundException {
        SpecialtyDTO createdSpecialty = specialtyService.create(SpecialtyDTO.builder()
                .name("cardiology")
                .office("Lima")
                .openHour(8)
                .closeHour(17)
                .build());
        createdSpecialty.setName("neurology");
        createdSpecialty.setOffice("Cusco");
        createdSpecialty.setOpenHour(9);
        createdSpecialty.setCloseHour(18);

        SpecialtyDTO updatedSpecialty = specialtyService.update(createdSpecialty);

        assertEquals(createdSpecialty.getId(), updatedSpecialty.getId());
        assertEquals("neurology", updatedSpecialty.getName());
        assertEquals("Cusco", updatedSpecialty.getOffice());
        assertEquals(9, updatedSpecialty.getOpenHour());
        assertEquals(18, updatedSpecialty.getCloseHour());
    }

    @Test
    void testFindSpecialtyById() throws SpecialtyNotFoundException {
        SpecialtyDTO createdSpecialty = specialtyService.create(SpecialtyDTO.builder()
                .name("cardiology")
                .office("Lima")
                .openHour(8)
                .closeHour(17)
                .build());

        SpecialtyDTO foundSpecialty = specialtyService.findById(createdSpecialty.getId());

        assertEquals(createdSpecialty.getId(), foundSpecialty.getId());
        assertEquals("cardiology", foundSpecialty.getName());
        assertEquals("Lima", foundSpecialty.getOffice());
    }

    @Test
    void testDeleteSpecialty() throws SpecialtyNotFoundException {
        SpecialtyDTO createdSpecialty = specialtyService.create(SpecialtyDTO.builder()
                .name("cardiology")
                .office("Lima")
                .openHour(8)
                .closeHour(17)
                .build());

        specialtyService.delete(createdSpecialty.getId());

        Integer deletedId = createdSpecialty.getId();
        assertThrows(SpecialtyNotFoundException.class, () -> specialtyService.findById(deletedId));
    }
}
