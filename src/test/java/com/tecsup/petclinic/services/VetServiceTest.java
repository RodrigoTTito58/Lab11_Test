package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
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
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    void testCreateVet() {
        VetDTO vetDTO = VetDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .email("ana.gomez@example.com")
                .phone("5551234567")
                .active(true)
                .build();

        VetDTO createdVet = vetService.create(vetDTO);

        assertNotNull(createdVet.getId());
        assertEquals("Ana", createdVet.getFirstName());
        assertEquals("Gomez", createdVet.getLastName());
        assertEquals("ana.gomez@example.com", createdVet.getEmail());
        assertEquals("5551234567", createdVet.getPhone());
        assertEquals(true, createdVet.isActive());
    }

    @Test
    void testUpdateVet() throws VetNotFoundException {
        VetDTO createdVet = vetService.create(VetDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .email("ana.gomez@example.com")
                .phone("5551234567")
                .active(true)
                .build());
        createdVet.setFirstName("Maria");
        createdVet.setPhone("5559876543");

        VetDTO updatedVet = vetService.update(createdVet);

        assertEquals(createdVet.getId(), updatedVet.getId());
        assertEquals("Maria", updatedVet.getFirstName());
        assertEquals("5559876543", updatedVet.getPhone());
    }

    @Test
    void testFindVetById() throws VetNotFoundException {
        VetDTO createdVet = vetService.create(VetDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .email("ana.gomez@example.com")
                .phone("5551234567")
                .active(true)
                .build());

        VetDTO foundVet = vetService.findById(createdVet.getId());

        assertEquals(createdVet.getId(), foundVet.getId());
        assertEquals("Ana", foundVet.getFirstName());
    }

    @Test
    void testDeleteVet() throws VetNotFoundException {
        VetDTO createdVet = vetService.create(VetDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .email("ana.gomez@example.com")
                .phone("5551234567")
                .active(true)
                .build());

        vetService.delete(createdVet.getId());

        Integer deletedId = createdVet.getId();
        assertThrows(VetNotFoundException.class, () -> vetService.findById(deletedId));
    }
}
