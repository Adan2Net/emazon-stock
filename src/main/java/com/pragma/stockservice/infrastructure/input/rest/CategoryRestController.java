package com.pragma.stockservice.infrastructure.input.rest;

import com.pragma.stockservice.application.dto.CategoryRequest;
import com.pragma.stockservice.application.dto.CategoryResponse;
import com.pragma.stockservice.application.handler.ICategoryHandler;
import com.pragma.stockservice.application.mapper.CategoryResponseMapper;
import com.pragma.stockservice.domain.model.Category;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoryRestController {

    private final ICategoryHandler categoryHandler;
    private final CategoryResponseMapper responseMapper;

    @Operation(summary = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created", content = @Content(mediaType
                    = "application/json", schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description
                    = "Invalid input", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("")
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        Category category = new Category(
                categoryRequest.getName(),
                categoryRequest.getDescription()
        );
        Category savedCategory = categoryHandler.save(category);
        CategoryResponse response = responseMapper.toCategoryResponse(savedCategory);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
