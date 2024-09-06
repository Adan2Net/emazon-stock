package com.pragma.stockservice.infrastructure.output.jpa.repository;

import com.pragma.stockservice.infrastructure.output.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {

    Optional<BrandEntity> findByName(@Param("name") String name);
}
