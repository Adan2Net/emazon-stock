package com.pragma.stockservice.application.dto.brand;

import java.util.List;

public record BrandPaginationResponse<T>(
        List<BrandResponse> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isFirst,
        boolean isLast
) {
}
