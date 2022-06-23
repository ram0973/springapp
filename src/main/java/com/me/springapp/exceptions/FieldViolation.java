package com.me.springapp.exceptions;

public record FieldViolation(String fieldName, String message) {
}
