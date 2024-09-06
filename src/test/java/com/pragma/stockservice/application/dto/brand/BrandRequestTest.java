package com.pragma.stockservice.application.dto.brand;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BrandRequestTest {

    @Test
    void testBrandRequest() {
        // Preparar datos
        String name = "Electronics";
        String description = "Various electronics";

        // Crear instancia de CategoryRequest usando el constructor con parámetros
        BrandRequest brandRequest = new BrandRequest(name, description);

        // Verificar que los valores se establecieron correctamente
        assertThat(brandRequest.getName()).isEqualTo(name);
        assertThat(brandRequest.getDescription()).isEqualTo(description);

        // Modificar valores
        String newName = "Home Appliances";
        String newDescription = "Various home appliances";
        brandRequest.setName(newName);
        brandRequest.setDescription(newDescription);

        // Verificar que los valores se modificaron correctamente
        assertThat(brandRequest.getName()).isEqualTo(newName);
        assertThat(brandRequest.getDescription()).isEqualTo(newDescription);
    }
}
