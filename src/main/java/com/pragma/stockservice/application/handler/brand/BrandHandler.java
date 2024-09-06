package com.pragma.stockservice.application.handler.brand;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandHandler implements IBrandHandler {

    private final IBrandPersistencePort persistencePort;

    @Override
    public Brand save(Brand brand) {
        persistencePort.findByName(brand.getName())
                .ifPresent(existingBrand -> {
                    throw new DuplicateBrandNameException("Brand with this name already exists");
                });

        return persistencePort.save(brand);
    }
}