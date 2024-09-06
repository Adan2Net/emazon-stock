package com.pragma.stockservice.infrastructure.input.rest;

import com.pragma.stockservice.application.dto.brand.BrandRequest;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.application.dto.category.CategoryResponse;
import com.pragma.stockservice.application.handler.brand.IBrandHandler;
import com.pragma.stockservice.application.mapper.brand.BrandResponseMapper;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.error.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("brands")
public class BrandRestController {

    private final IBrandHandler brandHandler;
    private final BrandResponseMapper responseMapper;

    @Operation(summary = "Add a new brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created", content = @Content(mediaType
                    = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description
                    = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<BrandResponse> saveBrand(@Valid @RequestBody BrandRequest brandRequest) {
        Brand brand = new Brand(
                brandRequest.getName(),
                brandRequest.getDescription()
        );
        Brand savedBrand = brandHandler.save(brand);
        BrandResponse response = responseMapper.toBrandResponse(savedBrand);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
