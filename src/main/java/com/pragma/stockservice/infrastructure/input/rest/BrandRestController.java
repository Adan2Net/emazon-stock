package com.pragma.stockservice.infrastructure.input.rest;

import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.application.dto.brand.BrandPaginationResponse;
import com.pragma.stockservice.application.dto.brand.BrandRequest;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.application.handler.brand.IBrandHandler;
import com.pragma.stockservice.application.mapper.brand.BrandResponseMapper;
import com.pragma.stockservice.domain.api.IBrandServicePort;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.error.ErrorResponse;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("brands")
public class BrandRestController {

    private final IBrandHandler brandHandler;
    private final BrandResponseMapper responseMapper;
    private final BrandEntityMapper entityMapper;
    private final IBrandServicePort servicePort;

    @Operation(summary = "Add a new brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Brand created", content = @Content(mediaType
                    = "application/json", schema = @Schema(implementation = BrandResponse.class))),
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

    @Operation(summary = "All brand")
    @GetMapping("/pagination")
    public BrandPaginationResponse<BrandResponse> getPaginationBrandByAscAndDesc(
            @RequestParam(defaultValue = "ASC") SortDirectionRequest sortDirectionRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return entityMapper.toBrandPaginationResponse(
                servicePort.getPaginationBrandByAscAndDesc(
                        responseMapper.toSortDirection(sortDirectionRequest), page, size));
    }
}
