package com.pragma.stockservice.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.stockservice.application.dto.brand.BrandRequest;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.application.handler.brand.IBrandHandler;
import com.pragma.stockservice.application.mapper.brand.BrandResponseMapper;
import com.pragma.stockservice.domain.model.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BrandRestControllerTest {

    @Mock
    private IBrandHandler brandHandler;

    @Mock
    private BrandResponseMapper responseMapper;

    @InjectMocks
    private BrandRestController brandRestController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(brandRestController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void saveBrand_ShouldReturnCreated_WhenBrandIsValid() throws Exception {
        // Arrange
        BrandRequest brandRequest = new BrandRequest("Brand Name", "Brand Description");
        Brand brand = new Brand("Brand Name", "Brand Description");
        Brand savedBrand = new Brand("Brand Name", "Brand Description");
        BrandResponse brandResponse = new BrandResponse(1L, "Brand Name", "Brand Description");

        when(brandHandler.save(any(Brand.class))).thenReturn(savedBrand);
        when(responseMapper.toBrandResponse(savedBrand)).thenReturn(brandResponse);

        // Act & Assert
        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(brandResponse)));
    }
}