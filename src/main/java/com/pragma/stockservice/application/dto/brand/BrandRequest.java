package com.pragma.stockservice.application.dto.brand;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    private String name;
    private String description;

    public BrandRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
