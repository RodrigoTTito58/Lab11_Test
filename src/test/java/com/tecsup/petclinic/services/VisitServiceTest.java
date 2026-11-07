package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("h2")
@Transactional
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Test
    void testCreateVisit() {
        VisitDTO createdVisit = visitService.create(newVisit());

        assertNotNull(createdVisit.getId());
        assertEquals(1, createdVisit.getPetId());
        assertEquals(1, createdVisit.getVetId());
        assertEquals(LocalDate.of(2026, 11, 6), createdVisit.getVisitDate());
        assertEquals("Routine checkup", createdVisit.getDescription());
        assertEquals(0, new BigDecimal("75.50").compareTo(createdVisit.getCost()));
    }

    @Test
    void testUpdateVisit() throws VisitNotFoundException {
        VisitDTO createdVisit = visitService.create(newVisit());
        createdVisit.setDescription("Updated checkup");
        createdVisit.setCost(new BigDecimal("90.00"));

        VisitDTO updatedVisit = visitService.update(createdVisit);

        assertEquals(createdVisit.getId(), updatedVisit.getId());
        assertEquals("Updated checkup", updatedVisit.getDescription());
        assertEquals(0, new BigDecimal("90.00").compareTo(updatedVisit.getCost()));
    }

    @Test
    void testFindVisitById() throws VisitNotFoundException {
        VisitDTO createdVisit = visitService.create(newVisit());

        VisitDTO foundVisit = visitService.findById(createdVisit.getId());

        assertEquals(createdVisit.getId(), foundVisit.getId());
        assertEquals(1, foundVisit.getPetId());
        assertEquals("Routine checkup", foundVisit.getDescription());
    }

    @Test
    void testDeleteVisit() throws VisitNotFoundException {
        VisitDTO createdVisit = visitService.create(newVisit());

        visitService.delete(createdVisit.getId());

        Integer deletedId = createdVisit.getId();
        assertThrows(VisitNotFoundException.class, () -> visitService.findById(deletedId));
    }

    private VisitDTO newVisit() {
        return VisitDTO.builder()
                .petId(1)
                .vetId(1)
                .visitDate(LocalDate.of(2026, 11, 6))
                .description("Routine checkup")
                .cost(new BigDecimal("75.50"))
                .build();
    }
}
