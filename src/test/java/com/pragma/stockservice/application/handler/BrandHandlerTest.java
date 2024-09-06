package com.pragma.stockservice.application.handler;

import com.pragma.stockservice.application.handler.brand.BrandHandler;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

class BrandHandlerTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandHandler brandHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveBrandSuccessfully() {
        // Arrange
        Brand brand = new Brand("ValidName", "ValidDescription");
        when(brandPersistencePort.findByName(anyString())).thenReturn(Optional.empty());
        when(brandPersistencePort.save(any(Brand.class))).thenReturn(brand);

        // Act
        Brand savedBrand = brandHandler.save(brand);

        // Assert
        verify(brandPersistencePort, times(1)).findByName("ValidName");
        verify(brandPersistencePort, times(1)).save(brand);
        assertEquals(brand, savedBrand);
    }

    @Test
    void shouldThrowExceptionWhenBrandNameAlreadyExists() {
        // Arrange
        Brand brand = new Brand("DuplicateName", "ValidDescription");
        when(brandPersistencePort.findByName(anyString())).thenReturn(Optional.of(brand));

        // Act & Assert
        assertThrows(DuplicateBrandNameException.class, () -> brandHandler.save(brand));

        verify(brandPersistencePort, times(1)).findByName("DuplicateName");
        verify(brandPersistencePort, never()).save(any(Brand.class));
    }
}
