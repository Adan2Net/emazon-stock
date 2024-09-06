package com.pragma.stockservice.domain.usercase;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.domain.usecase.BrandUseCase;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBrandSuccess() {
        // Arrange
        Brand brand = new Brand("Books", "Various books");

        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.empty());

        // Act & Assert
        assertDoesNotThrow(() -> brandUseCase.save(brand));
        verify(brandPersistencePort, times(1)).save(brand);
    }

    @Test
    void testSaveBrand_BrandAlreadyExists() {
        // Arrange
        Brand brand = new Brand("Books", "Various books");

        // Mock que simula que la categoría ya existe
        when(brandPersistencePort.findByName(brand.getName())).thenReturn(Optional.of(brand));

        // Act & Assert
        assertThrows(DuplicateBrandNameException.class, () -> brandUseCase.save(brand));

        // Verifica que el método saveCategory no se llamó
        verify(brandPersistencePort, times(0)).save(brand);
    }

    @Test
    void testSaveBrand_whenPersistenceFails_shouldThrowException() {
        // Arrange
        Brand brand = new Brand("Toys", "Various toys");

        doThrow(new RuntimeException("Database connection error"))
                .when(brandPersistencePort).save(any(Brand.class));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            brandUseCase.save(brand);
        });

        assertEquals("Database connection error", exception.getMessage());
    }
}
