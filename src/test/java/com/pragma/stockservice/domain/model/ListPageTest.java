package com.pragma.stockservice.domain.model;

import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListPageTest {

    @Test
    void testListPageConstructorAndGetters() {
        // Arrange
        List<Category> content = Collections.singletonList(new Category("Test Category", "Test Description"));
        int pageNumber = 1;
        int pageSize = 10;
        long totalElements = 100;
        int totalPages = 10;
        boolean isFirst = true;
        boolean isLast = false;

        // Act
        ListPage<Category> listPage = new ListPage<>(
                content, pageNumber, pageSize, totalElements, totalPages, isFirst, isLast
        );

        // Assert
        assertEquals(content, listPage.getContent(), "Content should match");
        assertEquals(pageNumber, listPage.getPageNumber(), "Page number should match");
        assertEquals(pageSize, listPage.getPageSize(), "Page size should match");
        assertEquals(totalElements, listPage.getTotalElements(), "Total elements should match");
        assertEquals(totalPages, listPage.getTotalPages(), "Total pages should match");
        assertTrue(listPage.isFirst(), "Should be the first page");
        assertEquals(isLast, listPage.isLast(), "Should not be the last page");
    }

    @Test
    void testSetters() {
        // Arrange
        ListPage<Category> listPage = new ListPage<>(
                Collections.emptyList(), 0, 0, 0, 0, false, false
        );

        List<Category> content = Collections.singletonList(new Category("Test Category", "Test Description"));
        int pageNumber = 2;
        int pageSize = 20;
        long totalElements = 200;
        int totalPages = 20;
        boolean isFirst = false;
        boolean isLast = true;

        // Act
        listPage.setContent(content);
        listPage.setPageNumber(pageNumber);
        listPage.setPageSize(pageSize);
        listPage.setTotalElements(totalElements);
        listPage.setTotalPages(totalPages);
        listPage.setFirst(isFirst);
        listPage.setLast(isLast);

        // Assert
        assertEquals(content, listPage.getContent(), "Content should match");
        assertEquals(pageNumber, listPage.getPageNumber(), "Page number should match");
        assertEquals(pageSize, listPage.getPageSize(), "Page size should match");
        assertEquals(totalElements, listPage.getTotalElements(), "Total elements should match");
        assertEquals(totalPages, listPage.getTotalPages(), "Total pages should match");
        assertTrue(!listPage.isFirst(), "Should not be the first page");
        assertEquals(isLast, listPage.isLast(), "Should be the last page");
    }
}