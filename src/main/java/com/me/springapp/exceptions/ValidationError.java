package com.me.springapp.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
public class ValidationError extends ApiError {
    @JsonProperty("validationErrors")
    List<FieldViolation> fieldViolations;
    ValidationError(List<FieldViolation> fieldViolations, HttpServletRequest req, Exception e, HttpStatus status) {
        super(req, e, status);
        this.fieldViolations = fieldViolations;
        this.message = "Validation error";
    }
}
