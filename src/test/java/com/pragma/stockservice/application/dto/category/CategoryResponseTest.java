package com.pragma.stockservice.application.dto.category;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryResponseTest {

    @Test
    void testCategoryResponseConstructorAndGettersSetters() {
        // Preparar datos
        Long id = 1L;
        String name = "Electronics";
        String description = "Various electronics";

        // Crear instancia de CategoryResponse usando el constructor
        CategoryResponse categoryResponse = new CategoryResponse(id, name, description);

        // Verificar que los valores se establecieron correctamente
        assertThat(categoryResponse.getId()).isEqualTo(id);
        assertThat(categoryResponse.getName()).isEqualTo(name);
        assertThat(categoryResponse.getDescription()).isEqualTo(description);

        // Modificar valores
        Long newId = 2L;
        String newName = "Home Appliances";
        String newDescription = "Various home appliances";
        categoryResponse.setId(newId);
        categoryResponse.setName(newName);
        categoryResponse.setDescription(newDescription);

        // Verificar que los valores se modificaron correctamente
        assertThat(categoryResponse.getId()).isEqualTo(newId);
        assertThat(categoryResponse.getName()).isEqualTo(newName);
        assertThat(categoryResponse.getDescription()).isEqualTo(newDescription);
    }
}
