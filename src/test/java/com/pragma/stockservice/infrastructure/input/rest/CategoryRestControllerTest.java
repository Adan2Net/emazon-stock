package com.pragma.stockservice.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.stockservice.application.dto.SortDirectionRequest;
import com.pragma.stockservice.application.dto.category.CategoryPaginationResponse;
import com.pragma.stockservice.application.dto.category.CategoryRequest;
import com.pragma.stockservice.application.dto.category.CategoryResponse;
import com.pragma.stockservice.application.handler.category.ICategoryHandler;
import com.pragma.stockservice.application.mapper.category.CategoryResponseMapper;
import com.pragma.stockservice.domain.api.ICategoryServicePort;
import com.pragma.stockservice.domain.model.Category;
import com.pragma.stockservice.domain.model.ListPage;
import com.pragma.stockservice.domain.model.SortDirection;
import com.pragma.stockservice.infrastructure.output.jpa.mapper.CategoryEntityMapper;
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

class CategoryRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ICategoryHandler categoryHandler;

    @Mock
    private CategoryResponseMapper responseMapper;

    @Mock
    private ICategoryServicePort servicePort;

    @Mock
    private CategoryEntityMapper entityMapper;

    @InjectMocks
    private CategoryRestController categoryRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestController).build();
    }

    @Test
    void saveCategory_WhenValidRequest_ShouldReturnCreatedStatus() throws Exception {
        // Arrange
        CategoryRequest request = new CategoryRequest("Electronics", "Electronic gadgets");

        Category category = new Category("Electronics", "Electronic gadgets");
        CategoryResponse response = new CategoryResponse(1L, "Electronics", "Electronic gadgets");

        when(responseMapper.toCategoryResponse(category)).thenReturn(response);
        when(categoryHandler.save(any(Category.class))).thenReturn(category);

        // Act
        MvcResult result = mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.description").value("Electronic gadgets"))
                .andReturn();

        // Assert
        verify(categoryHandler, times(1)).save(any(Category.class));
    }



    @Test
    void getPaginationCategoriesByAscAndDesc_ShouldReturnCategoryPaginationResponse() throws Exception {
        // Arrange
        SortDirectionRequest sortDirectionRequest = SortDirectionRequest.ASC;
        SortDirection sortDirection = SortDirection.ASC;
        int page = 0;
        int size = 10;

        List<Category> categoryList = List.of(new Category("Electronics", "Devices"));
        ListPage<Category> listPage = new ListPage<>(categoryList, page, size, 1L, 1, true, true);
        CategoryPaginationResponse<CategoryResponse> paginationResponse = new CategoryPaginationResponse<>(
                List.of(new CategoryResponse(1L, "Electronics", "Devices")),
                0,
                10,
                1,
                1,
                true,
                true
        );

        when(responseMapper.toSortDirection(sortDirectionRequest)).thenReturn(sortDirection);
        when(servicePort.getPaginationCategoriesByAscAndDesc(sortDirection, page, size)).thenReturn(listPage);
        when(entityMapper.toCategoryPaginationResponse(listPage)).thenReturn(paginationResponse);

        // Act
        MvcResult result = mockMvc.perform(get("/categories/pagination")
                        .param("sortDirection", sortDirectionRequest.name())
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].name").value("Electronics"))
                .andExpect(jsonPath("$.content[0].description").value("Devices"))
                .andReturn();

        // Assert
        verify(servicePort, times(1)).getPaginationCategoriesByAscAndDesc(sortDirection, page, size);
    }
}