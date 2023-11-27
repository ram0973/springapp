package com.me.springapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    public ValidationError onMethodArgumentNotValidException(
        HttpServletRequest req, @NotNull MethodArgumentNotValidException e) {
        final List<FieldViolation> fieldViolations = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
            .toList();
        return new ValidationError(fieldViolations, req, e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ApiError handleThereIsNoSuchEntityException(HttpServletRequest req, Exception e) {
        //return new CommonError(req, e);
        return new ApiError(req, e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ValidationError handleEmailAlreadyInUseException(HttpServletRequest req, Exception e) {
        return new ValidationError(List.of(new FieldViolation("email", e.getLocalizedMessage())),
            req, e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    protected ApiError handleBadCredentialsException(HttpServletRequest req, Exception e) {
        return new ApiError(req, e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidJWTException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ApiError handleInvalidJWTException(HttpServletRequest req, @NotNull Exception e) {
        log.error(e.getLocalizedMessage());
        return new ApiError(req, e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected ApiError handleMethodArgumentTypeMismatchException(HttpServletRequest req, Exception e) {
        return new ApiError(req, e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected ApiError handleMEmptyResultDataAccessException(HttpServletRequest req, Exception e) {
        return new ApiError(req, e, HttpStatus.NOT_FOUND);
    }
}

