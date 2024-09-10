package com.pragma.stockservice.infrastructure.output.jpa.adapter;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.spi.IBrandPersistencePort;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import com.pragma.stockservice.infrastructure.output.jpa.repository.IBrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
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

    @Override
    public ListPage<Brand> getPaginationBrand(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BrandEntity> brandPage = brandRepository.findAll(pageable);

        List<Brand> brandContent = brandPage.getContent()
                .stream()
                .map(brandEntityMapper::toBrand)
                .toList();

        return new ListPage<>(
                brandContent,
                brandPage.getNumber(),
                brandPage.getSize(),
                brandPage.getTotalElements(),
                brandPage.getTotalPages(),
                brandPage.isFirst(),
                brandPage.isLast()
        );
    }
}
