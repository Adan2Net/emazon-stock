package com.pragma.stockservice.domain.spi;

import com.pragma.stockservice.domain.model.Brand;

import java.util.Optional;

public interface IBrandPersistencePort {
    Brand save(Brand brand);
    Optional<Brand> findByName(String name);
}
