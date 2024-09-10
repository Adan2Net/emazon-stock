package com.pragma.stockservice.domain.api;

import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;

public interface IBrandServicePort {

    Brand save(Brand brand);
    ListPage<Brand> getPaginationBrandByAscAndDesc(SortDirection sortDirection, int page, int size);
}
