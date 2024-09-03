package com.pragma.stockservice.application.dto;

import java.util.List;

public record CategoryPaginationResponse<T>(
        List<CategoryResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isFirst,
        boolean isLast
) {
}
