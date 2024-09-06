package com.pragma.stockservice.infrastructure.exceptionhandler;

import com.pragma.stockservice.domain.model.error.ErrorResponse;
import com.pragma.stockservice.infrastructure.exception.BrandNotFoundException;
import com.pragma.stockservice.infrastructure.exception.CategoryNotFoundException;
import com.pragma.stockservice.infrastructure.exception.DuplicateBrandNameException;
import com.pragma.stockservice.infrastructure.exception.DuplicateCategoryNameException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

    // Manejador de excepciones para CategoryNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ErrorResponse handleCategoryNotFoundException() {
        logger.error("Category not found");
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                ExceptionResponse.CATEGORY_NOT_FOUND.getMessage(),
                Collections.singletonList("The category you are trying to access does not exist."),
                LocalDateTime.now()
        );
    }

    // Manejador de excepciones para BrandNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BrandNotFoundException.class)
    public ErrorResponse handleBrandNotFoundException() {
        logger.error("Brand not found");
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.toString(),
                ExceptionResponse.BRAND_NOT_FOUND.getMessage(),
                Collections.singletonList("The brand you are trying to access does not exist."),
                LocalDateTime.now()
        );
    }

    // Manejador de excepciones para IllegalArgumentException (diferencia entre Category y Brand)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.error("Validation error", exception);

        String entity = determineEntityFromMessage(exception.getMessage());
        ExceptionResponse errorResponse = entity.equals("Brand") ?
                ExceptionResponse.INVALID_BRAND_PARAMETERS :
                ExceptionResponse.INVALID_CATEGORY_PARAMETERS;

        return new ErrorResponse(
                errorResponse.getCode(),
                errorResponse.getMessage(),
                Collections.singletonList("Validation failed for the input parameters. " + exception.getMessage()),
                LocalDateTime.now()
        );
    }

    // Manejador de excepciones para ConstraintViolationException (diferencia entre Category y Brand)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        logger.error("Validation error during persistence", exception);

        String entity = determineEntityFromViolations(exception.getConstraintViolations().toString());
        ExceptionResponse errorResponse = entity.equals("Brand") ?
                ExceptionResponse.INVALID_BRAND_PARAMETERS :
                ExceptionResponse.INVALID_CATEGORY_PARAMETERS;

        List<String> details = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).toList();

        return new ErrorResponse(
                errorResponse.getCode(),
                errorResponse.getMessage(),
                details,
                LocalDateTime.now()
        );
    }

    // Método auxiliar para determinar la entidad desde el mensaje de error
    private String determineEntityFromMessage(String message) {
        if (message.toLowerCase().contains("brand")) {
            return "Brand";
        }
        return "Category";
    }

    // Método auxiliar para determinar la entidad desde las violaciones
    private String determineEntityFromViolations(String violations) {
        if (violations.toLowerCase().contains("brand")) {
            return "Brand";
        }
        return "Category";
    }

    // Manejador de excepciones para DuplicateCategoryNameException
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateCategoryNameException.class)
    public ErrorResponse handleDuplicateCategoryNameException(DuplicateCategoryNameException exception) {
        logger.error("Duplicate category name", exception);
        return new ErrorResponse(
                HttpStatus.CONFLICT.toString(),
                ExceptionResponse.DUPLICATE_CATEGORY_NAME.getMessage(),
                Collections.singletonList("A category with this name already exists. Please choose a different name."),
                LocalDateTime.now()
        );
    }

    // Manejador de excepciones para DuplicateBrandNameException
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateBrandNameException.class)
    public ErrorResponse handleDuplicateBrandNameException(DuplicateBrandNameException exception) {
        logger.error("Duplicate brand name", exception);
        return new ErrorResponse(
                HttpStatus.CONFLICT.toString(),
                ExceptionResponse.DUPLICATE_BRAND_NAME.getMessage(),
                Collections.singletonList("A brand with this name already exists. Please choose a different name."),
                LocalDateTime.now()
        );
    }

    // Manejador genérico de excepciones
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericError(Exception exception) {
        logger.error("Internal server error", exception);
        return new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                ExceptionResponse.GENERIC_ERROR.getMessage(),
                Collections.singletonList(exception.getMessage()),
                LocalDateTime.now()
        );
    }
}