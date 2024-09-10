package com.pragma.stockservice.domain.usecase;

import com.pragma.stockservice.domain.api.IBrandServicePort;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import com.pragma.stockservice.infrastructure.exception.InvalidSortDirectionException;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
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

    @Override
    public ListPage<Brand> getPaginationBrandByAscAndDesc(SortDirection sortDirection, int page, int size) {
        // Implementación del método de paginación y ordenación
        ListPage<Brand> listPageBrand = brandPersistencePort.getPaginationBrand(page, size);

        if ("ASC".equalsIgnoreCase(sortDirection.name()) || "DESC".equalsIgnoreCase(sortDirection.name())) {
            listPageBrand.setContent(
                    listPageBrand.getContent().stream()
                            .sorted("ASC".equalsIgnoreCase(sortDirection.name()) ?
                                    Comparator.comparing(Brand::getName) :
                                    Comparator.comparing(Brand::getName).reversed())
                            .toList()
            );
        } else {
            throw new InvalidSortDirectionException();
        }

        return listPageBrand;
    }
}
