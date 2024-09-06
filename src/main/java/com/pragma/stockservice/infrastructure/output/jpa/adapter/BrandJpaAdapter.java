package com.pragma.stockservice.infrastructure.output.jpa.adapter;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.pragma.stockservice.infrastructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BrandJpaAdapter implements IBrandPersistencePort {

    private final IBrandRepository brandRepository;
    private final BrandEntityMapper brandEntityMapper;

    @Override
    public Brand save(Brand brand) {
        if (brandRepository.findByName(brand.getName()).isPresent()) {
            throw new DuplicateBrandNameException("Brand with this name already exists");
        }
        BrandEntity brandEntity = brandEntityMapper.toEntity(brand);
        BrandEntity savedEntity = brandRepository.save(brandEntity);
        return brandEntityMapper.toBrand(savedEntity);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name)
                .map(brandEntityMapper::toBrand);
    }
}
