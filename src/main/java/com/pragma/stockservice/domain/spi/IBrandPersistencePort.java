package com.pragma.stockservice.domain.spi;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;

import java.util.Optional;

public interface IBrandPersistencePort {
    Brand save(Brand brand);
    Optional<Brand> findByName(String name);
    ListPage<Brand> getPaginationBrand(int page, int size);
}
