package com.pragma.stockservice.infrastructure.exceptionhandler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExceptionResponseTest {

    @Test
    void testExceptionResponse() {
        // Test CATEGORY_NOT_FOUND
        ExceptionResponse categoryNotFound = ExceptionResponse.CATEGORY_NOT_FOUND;
        assertEquals("404 Not Found", categoryNotFound.getCode(), "The code for CATEGORY_NOT_FOUND should be '404 Not Found'");
        assertEquals("Category not found.", categoryNotFound.getMessage(), "The message for CATEGORY_NOT_FOUND should be 'Category not found.'");

        // Test BRAND_NOT_FOUND
        ExceptionResponse brandNotFound = ExceptionResponse.BRAND_NOT_FOUND;
        assertEquals("404 Not Found", brandNotFound.getCode(), "The code for BRAND_NOT_FOUND should be '404 Not Found'");
        assertEquals("Brand not found.", brandNotFound.getMessage(), "The message for BRAND_NOT_FOUND should be 'Category not found.'");

        // Test INVALID_CATEGORY_PARAMETERS
        ExceptionResponse invalidCategoryParameters = ExceptionResponse.INVALID_CATEGORY_PARAMETERS;
        assertEquals("400 Bad Request", invalidCategoryParameters.getCode(), "The code for INVALID_CATEGORY_PARAMETERS should be '400 Bad Request'");
        assertEquals("Invalid category parameters.", invalidCategoryParameters.getMessage(), "The message for INVALID_CATEGORY_PARAMETERS should be 'Invalid category parameters.'");

        // Test INVALID_BRAND_PARAMETERS
        ExceptionResponse invalidBrandParameters = ExceptionResponse.INVALID_BRAND_PARAMETERS;
        assertEquals("400 Bad Request", invalidBrandParameters.getCode(), "The code for INVALID_BRAND_PARAMETERS should be '400 Bad Request'");
        assertEquals("Invalid brand parameters.", invalidBrandParameters.getMessage(), "The message for INVALID_BRAND_PARAMETERS should be 'Invalid category parameters.'");

        // Test DUPLICATE_CATEGORY_NAME
        ExceptionResponse duplicateCategoryName = ExceptionResponse.DUPLICATE_CATEGORY_NAME;
        assertEquals("409 Conflict", duplicateCategoryName.getCode(), "The code for DUPLICATE_CATEGORY_NAME should be '409 Conflict'");
        assertEquals("Category with this name already exists.", duplicateCategoryName.getMessage(), "The message for DUPLICATE_CATEGORY_NAME should be 'Category with this name already exists.'");

        // Test DUPLICATE_BRAND_NAME
        ExceptionResponse duplicateBrandName = ExceptionResponse.DUPLICATE_BRAND_NAME;
        assertEquals("409 Conflict", duplicateBrandName.getCode(), "The code for DUPLICATE_BRAND_NAME should be '409 Conflict'");
        assertEquals("Brand with this name already exists.", duplicateBrandName.getMessage(), "The message for DUPLICATE_BRAND_NAME should be 'Category with this name already exists.'");

        // Test GENERIC_ERROR
        ExceptionResponse genericError = ExceptionResponse.GENERIC_ERROR;
        assertEquals("500", genericError.getCode(), "The code for GENERIC_ERROR should be '500'");
        assertEquals("An unexpected error occurred.", genericError.getMessage(), "The message for GENERIC_ERROR should be 'An unexpected error occurred.'");
    }
}