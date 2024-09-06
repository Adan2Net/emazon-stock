package com.pragma.stockservice.domain.usecase;

import com.pragma.stockservice.domain.api.IBrandServicePort;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;

public class BrandUseCase implements IBrandServicePort {

    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand save(Brand brand) {
        // Verifica si ya existe una marca con el mismo nombre
        if (brandPersistencePort.findByName(brand.getName()).isPresent()) {
            throw new DuplicateBrandNameException("Brand with name " + brand.getName() + " already exists.");
        }

        // Si no existe, procede a guardarla
        return brandPersistencePort.save(brand);
    }
}
