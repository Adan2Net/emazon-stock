package com.pragma.stockservice.application.dto.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequest {

    private String name;
    private String description;

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }
}