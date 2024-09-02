package com.pragma.stockservice.domain.model.error;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {

    private final String code;
    private final String message;
    private final List<String> details;
    private final LocalDateTime timestamp;

    public ErrorResponse(String code, String message, List<String> details, LocalDateTime timestamp) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getDetails() {
        return details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
