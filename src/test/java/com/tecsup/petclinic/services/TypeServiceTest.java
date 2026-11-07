package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.TypeDTO;
import com.tecsup.petclinic.exceptions.TypeNotFoundException;
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
public class TypeServiceTest {

    @Autowired
    private TypeService typeService;

    @Test
    void testCreateType() {
        TypeDTO createdType = typeService.create(newType());

        assertNotNull(createdType.getId());
        assertEquals("ferret", createdType.getName());
        assertEquals("Domestic ferret", createdType.getDescription());
        assertEquals(true, createdType.isActive());
        assertEquals("small", createdType.getSizeCategory());
        assertEquals(8, createdType.getAverageLifespan());
        assertEquals("medium", createdType.getCareLevel());
    }

    @Test
    void testUpdateType() throws TypeNotFoundException {
        TypeDTO createdType = typeService.create(newType());
        createdType.setName("guinea pig");
        createdType.setDescription("Domestic guinea pig");
        createdType.setAverageLifespan(7);

        TypeDTO updatedType = typeService.update(createdType);

        assertEquals(createdType.getId(), updatedType.getId());
        assertEquals("guinea pig", updatedType.getName());
        assertEquals("Domestic guinea pig", updatedType.getDescription());
        assertEquals(7, updatedType.getAverageLifespan());
    }

    @Test
    void testFindTypeById() throws TypeNotFoundException {
        TypeDTO createdType = typeService.create(newType());

        TypeDTO foundType = typeService.findById(createdType.getId());

        assertEquals(createdType.getId(), foundType.getId());
        assertEquals("ferret", foundType.getName());
    }

    @Test
    void testDeleteType() throws TypeNotFoundException {
        TypeDTO createdType = typeService.create(newType());

        typeService.delete(createdType.getId());

        Integer deletedId = createdType.getId();
        assertThrows(TypeNotFoundException.class, () -> typeService.findById(deletedId));
    }

    private TypeDTO newType() {
        return TypeDTO.builder()
                .name("ferret")
                .description("Domestic ferret")
                .active(true)
                .sizeCategory("small")
                .averageLifespan(8)
                .careLevel("medium")
                .build();
    }
}
