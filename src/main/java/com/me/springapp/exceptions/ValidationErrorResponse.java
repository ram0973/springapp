package com.me.springapp.exceptions;

import java.util.List;

public record ValidationErrorResponse(List<FieldViolation> fieldViolations) {
}
