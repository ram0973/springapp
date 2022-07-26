package com.me.springapp.exceptions;

import java.util.List;

public record ValidationError(List<FieldViolation> fieldViolations) {
}
