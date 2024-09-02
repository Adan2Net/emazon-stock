package com.pragma.stockservice.infrastructure.exceptionhandler;

import com.pragma.stockservice.domain.model.error.ErrorResponse;
import com.pragma.stockservice.infrastructure.exception.CategoryNotFoundException;
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
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        logger.error("Validation error", exception);
        return new ErrorResponse(
                ExceptionResponse.INVALID_CATEGORY_PARAMETERS.getCode(),
                ExceptionResponse.INVALID_CATEGORY_PARAMETERS.getMessage(),
                Collections.singletonList("Validation failed for the input parameters. " + exception.getMessage()),
                LocalDateTime.now()
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException exception) {
        logger.error("Validation error during persistence", exception);
        List<String> details = exception.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage()).toList();
        return new ErrorResponse(
                ExceptionResponse.INVALID_CATEGORY_PARAMETERS.getCode(),
                ExceptionResponse.INVALID_CATEGORY_PARAMETERS.getMessage(),
                details,
                LocalDateTime.now()
        );
    }

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