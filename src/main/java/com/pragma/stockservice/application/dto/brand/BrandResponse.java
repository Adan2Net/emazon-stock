package com.pragma.stockservice.application.dto.brand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandResponse {

    private Long id;
    private String name;
    private String description;

    public BrandResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}