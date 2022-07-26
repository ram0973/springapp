package com.me.springapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationError onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<FieldViolation> fieldViolations = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
            .toList();
        return new ValidationError(fieldViolations);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected CommonError handleThereIsNoSuchEntityException(HttpServletRequest req, Exception e) {
        return new CommonError(req, e);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected CommonError handleEmailAlreadyInUseException(HttpServletRequest req, Exception e) {
        return new CommonError(req, e);
    }

    @ExceptionHandler(InvalidJWTException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected CommonError handleInvalidJWTException(HttpServletRequest req, Exception e) {
        log.error(e.getLocalizedMessage());
        return new CommonError(req, e);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    protected CommonError handleMethodArgumentTypeMismatchException(HttpServletRequest req, Exception e) {
        return new CommonError(req, e);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected CommonError handleMEmptyResultDataAccessException(HttpServletRequest req, Exception e) {
        return new CommonError(req, e);
    }
}

