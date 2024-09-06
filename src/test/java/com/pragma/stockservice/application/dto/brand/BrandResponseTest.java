package com.pragma.stockservice.application.dto.brand;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BrandResponseTest {

    @Test
    void testBrandResponse() {
        // Preparar datos
        Long id = 1L;
        String name = "Electronics";
        String description = "Various electronics";

        // Crear instancia de CategoryResponse usando el constructor
        BrandResponse brandResponse = new BrandResponse(id, name, description);

        // Verificar que los valores se establecieron correctamente
        assertThat(brandResponse.getId()).isEqualTo(id);
        assertThat(brandResponse.getName()).isEqualTo(name);
        assertThat(brandResponse.getDescription()).isEqualTo(description);

        // Modificar valores
        Long newId = 2L;
        String newName = "Home Appliances";
        String newDescription = "Various home appliances";
        brandResponse.setId(newId);
        brandResponse.setName(newName);
        brandResponse.setDescription(newDescription);

        // Verificar que los valores se modificaron correctamente
        assertThat(brandResponse.getId()).isEqualTo(newId);
        assertThat(brandResponse.getName()).isEqualTo(newName);
        assertThat(brandResponse.getDescription()).isEqualTo(newDescription);
    }
}
