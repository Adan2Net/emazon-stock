package com.pragma.stockservice.application.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CategoryRequestTest {

    @Test
    void testCategoryRequestConstructorAndGettersSetters() {
        // Preparar datos
        String name = "Electronics";
        String description = "Various electronics";

        // Crear instancia de CategoryRequest usando el constructor con parámetros
        CategoryRequest categoryRequest = new CategoryRequest(name, description);

        // Verificar que los valores se establecieron correctamente
        assertThat(categoryRequest.getName()).isEqualTo(name);
        assertThat(categoryRequest.getDescription()).isEqualTo(description);

        // Modificar valores
        String newName = "Home Appliances";
        String newDescription = "Various home appliances";
        categoryRequest.setName(newName);
        categoryRequest.setDescription(newDescription);

        // Verificar que los valores se modificaron correctamente
        assertThat(categoryRequest.getName()).isEqualTo(newName);
        assertThat(categoryRequest.getDescription()).isEqualTo(newDescription);
    }
}