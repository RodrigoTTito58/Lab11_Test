package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
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
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    void testCreateOwner() {
        OwnerDTO ownerDTO = OwnerDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .address("Av. Siempre Viva 123")
                .city("Lima")
                .telephone("5551234567")
                .build();

        OwnerDTO createdOwner = ownerService.create(ownerDTO);

        assertNotNull(createdOwner.getId());
        assertEquals("Ana", createdOwner.getFirstName());
        assertEquals("Gomez", createdOwner.getLastName());
        assertEquals("Av. Siempre Viva 123", createdOwner.getAddress());
        assertEquals("Lima", createdOwner.getCity());
        assertEquals("5551234567", createdOwner.getTelephone());
    }

    @Test
    void testUpdateOwner() throws OwnerNotFoundException {
        OwnerDTO createdOwner = ownerService.create(OwnerDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .address("Av. Siempre Viva 123")
                .city("Lima")
                .telephone("5551234567")
                .build());
        createdOwner.setFirstName("Maria");
        createdOwner.setCity("Arequipa");
        createdOwner.setTelephone("5559876543");

        OwnerDTO updatedOwner = ownerService.update(createdOwner);

        assertEquals(createdOwner.getId(), updatedOwner.getId());
        assertEquals("Maria", updatedOwner.getFirstName());
        assertEquals("Arequipa", updatedOwner.getCity());
        assertEquals("5559876543", updatedOwner.getTelephone());
    }

    @Test
    void testFindOwnerById() throws OwnerNotFoundException {
        OwnerDTO createdOwner = ownerService.create(OwnerDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .address("Av. Siempre Viva 123")
                .city("Lima")
                .telephone("5551234567")
                .build());

        OwnerDTO foundOwner = ownerService.findById(createdOwner.getId());

        assertEquals(createdOwner.getId(), foundOwner.getId());
        assertEquals("Ana", foundOwner.getFirstName());
        assertEquals("Gomez", foundOwner.getLastName());
    }

    @Test
    void testDeleteOwner() throws OwnerNotFoundException {
        OwnerDTO createdOwner = ownerService.create(OwnerDTO.builder()
                .firstName("Ana")
                .lastName("Gomez")
                .address("Av. Siempre Viva 123")
                .city("Lima")
                .telephone("5551234567")
                .build());

        ownerService.delete(createdOwner.getId());

        Integer deletedId = createdOwner.getId();
        assertThrows(OwnerNotFoundException.class, () -> ownerService.findById(deletedId));
    }
}
