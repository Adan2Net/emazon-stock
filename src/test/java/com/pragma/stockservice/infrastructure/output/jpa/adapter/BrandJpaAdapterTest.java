package com.pragma.stockservice.infrastructure.output.jpa.adapter;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.pragma.stockservice.infrastructure.output.jpa.repository.IBrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandJpaAdapterTest {

    @Mock
    private IBrandRepository brandRepository;

    @Mock
    private BrandEntityMapper brandEntityMapper;

    @InjectMocks
    private BrandJpaAdapter brandJpaAdapter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveBrand_Success() {
        // Arrange
        Brand brand = new Brand("Books", "Various books");
        BrandEntity brandEntity = new BrandEntity();
        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.empty());
        when(brandEntityMapper.toEntity(brand)).thenReturn(brandEntity);
        when(brandRepository.save(brandEntity)).thenReturn(brandEntity);
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        Brand savedBrand = brandJpaAdapter.save(brand);

        // Assert
        assertEquals(brand.getName(), savedBrand.getName());
        verify(brandRepository, times(1)).save(brandEntity);
    }

    @Test
    void testSaveBrand_BrandAlreadyExists() {
        // Arrange
        Brand brand = new Brand("Books", "Various books");
        BrandEntity existingBrandEntity = new BrandEntity();
        when(brandRepository.findByName(brand.getName())).thenReturn(Optional.of(existingBrandEntity));

        // Act & Assert
        assertThrows(DuplicateBrandNameException.class, () -> brandJpaAdapter.save(brand));

        // Verifica que el método save no se llame
        verify(brandRepository, times(0)).save(any(BrandEntity.class));
    }

    @Test
    void testFindByName_Success() {
        // Arrange
        String brandName = "Books";
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand(brandName, "Various books");
        when(brandRepository.findByName(brandName)).thenReturn(Optional.of(brandEntity));
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        Optional<Brand> foundBrand = brandJpaAdapter.findByName(brandName);

        // Assert
        assertTrue(foundBrand.isPresent());
        assertEquals(brandName, foundBrand.get().getName());
        verify(brandRepository, times(1)).findByName(brandName);
    }

    @Test
    void testFindByName_NotFound() {
        // Arrange
        String brandName = "NonExistentBrand";
        when(brandRepository.findByName(brandName)).thenReturn(Optional.empty());

        // Act
        Optional<Brand> foundBrand = brandJpaAdapter.findByName(brandName);

        // Assert
        assertFalse(foundBrand.isPresent());
        verify(brandRepository, times(1)).findByName(brandName);
    }

    @Test
    void testGetPaginationBrand_Success() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        BrandEntity brandEntity = new BrandEntity();
        Brand brand = new Brand("Books", "Various books");
        Page<BrandEntity> brandPage = new PageImpl<>(Collections.singletonList(brandEntity), pageable, 1);

        when(brandRepository.findAll(pageable)).thenReturn(brandPage);
        when(brandEntityMapper.toBrand(brandEntity)).thenReturn(brand);

        // Act
        ListPage<Brand> result = brandJpaAdapter.getPaginationBrand(page, size);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
        verify(brandRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetPaginationBrand_EmptyPage() {
        // Arrange
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        Page<BrandEntity> emptyPage = new PageImpl<>(Collections.emptyList(), pageable, 0);

        when(brandRepository.findAll(pageable)).thenReturn(emptyPage);

        // Act
        ListPage<Brand> result = brandJpaAdapter.getPaginationBrand(page, size);

        // Assert
        assertEquals(0, result.getTotalElements());
        assertTrue(result.getContent().isEmpty());
        verify(brandRepository, times(1)).findAll(pageable);
    }
}
