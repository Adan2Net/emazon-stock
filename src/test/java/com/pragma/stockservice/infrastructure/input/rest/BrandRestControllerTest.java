package com.pragma.stockservice.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.application.dto.brand.BrandPaginationResponse;
import com.pragma.stockservice.application.dto.brand.BrandRequest;
import com.pragma.stockservice.application.dto.brand.BrandResponse;
import com.pragma.stockservice.application.handler.brand.IBrandHandler;
import com.pragma.stockservice.application.mapper.brand.BrandResponseMapper;
import com.pragma.stockservice.domain.api.IBrandServicePort;
import com.pragma.stockservice.domain.model.Brand;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.BrandEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BrandRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IBrandHandler brandHandler;

    @Mock
    private BrandResponseMapper responseMapper;

    @Mock
    private IBrandServicePort servicePort;

    @Mock
    private BrandEntityMapper entityMapper;

    @InjectMocks
    private BrandRestController brandRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(brandRestController).build();
    }

    @Test
    void saveBrand_WhenValidRequest_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        BrandRequest request = new BrandRequest("Apple", "Technology company");

        Brand brand = new Brand("Apple", "Technology company");
        BrandResponse response = new BrandResponse(1L, "Apple", "Technology company");

        when(responseMapper.toBrandResponse(brand)).thenReturn(response);
        when(brandHandler.save(any(Brand.class))).thenReturn(brand);

        // Act
        MvcResult result = mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Apple"))
                .andExpect(jsonPath("$.description").value("Technology company"))
                .andReturn();

        // Assert
        verify(brandHandler, times(1)).save(any(Brand.class));
    }

    @Test
    void getPaginationBrandsByAscAndDesc_ShouldReturnBrandPaginationResponse() throws Exception {
        // Arrange
        SortDirectionRequest sortDirectionRequest = SortDirectionRequest.ASC;
        SortDirection sortDirection = SortDirection.ASC;
        int page = 0;
        int size = 10;

        List<Brand> brandList = List.of(new Brand("Apple", "Technology company"));
        ListPage<Brand> listPage = new ListPage<>(brandList, page, size, 1L, 1, true, true);
        BrandPaginationResponse<BrandResponse> paginationResponse = new BrandPaginationResponse<>(
                List.of(new BrandResponse(1L, "Apple", "Technology company")),
                0,
                10,
                1,
                1,
                true,
                true
        );

        when(responseMapper.toSortDirection(sortDirectionRequest)).thenReturn(sortDirection);
        when(servicePort.getPaginationBrandByAscAndDesc(sortDirection, page, size)).thenReturn(listPage);
        when(entityMapper.toBrandPaginationResponse(listPage)).thenReturn(paginationResponse);

        // Act
        MvcResult result = mockMvc.perform(get("/brands/pagination")
                        .param("sortDirection", sortDirectionRequest.name())
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value("Apple"))
                .andExpect(jsonPath("$.content[0].description").value("Technology company"))
                .andReturn();

        // Assert
        verify(servicePort, times(1)).getPaginationBrandByAscAndDesc(sortDirection, page, size);
    }
}