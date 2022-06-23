package com.me.springapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(
        MethodArgumentNotValidException e
    ) {
        final List<FieldViolation> fieldViolations = e.getBindingResult().getFieldErrors().stream()
            .map(error -> new FieldViolation(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());
        return new ValidationErrorResponse(fieldViolations);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    protected ResponseEntity<NotFoundException> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        log.trace("Record not found: {}", e.getMessage());
        return new ResponseEntity<>(new NotFoundException(String.format("Record not found : %s", e.getMessage())),
            HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchArticleException.class)
    protected ResponseEntity<NotFoundException> handleThereIsNoSuchArticleException() {
        return new ResponseEntity<>(new NotFoundException("There is no such article"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchArticlesException.class)
    protected ResponseEntity<NotFoundException> handleThereIsNoSuchArticlesException() {
        return new ResponseEntity<>(new NotFoundException("There is no such articles"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchUserException.class)
    protected ResponseEntity<NotFoundException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new NotFoundException("There is no such user"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchRoleException.class)
    protected ResponseEntity<NotFoundException> handleThereIsNoSuchRoleException() {
        return new ResponseEntity<>(new NotFoundException("There is no such roles"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchUsersException.class)
    protected ResponseEntity<NoContentException> handleNoUsersException() {
        return new ResponseEntity<>(new NoContentException("There is no users"), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    protected ResponseEntity<BadRequestException> handleEmailAlreadyInUseException() {
        return new ResponseEntity<>(new BadRequestException("Email already in use"), HttpStatus.BAD_REQUEST);
    }

    private record NotFoundException(String message) {
    }

    private record NoContentException(String message) {
    }

    private record BadRequestException(String message) {
    }
}

