package com.me.springapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchUserException.class)
    protected ResponseEntity<NotFoundException> handleThereIsNoSuchUserException() {
        return new ResponseEntity<>(new NotFoundException("There is no such user"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchRoleException.class)
    protected ResponseEntity<NotFoundException> handleThereIsNoSuchRoleException() {
        return new ResponseEntity<>(new NotFoundException("There is no such role"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchUsersException.class)
    protected ResponseEntity<NoContentException> handleNoUsersException() {
        return new ResponseEntity<>(new NoContentException("There is no users"), HttpStatus.NO_CONTENT);
    }

    @Data
    @AllArgsConstructor
    private static class NotFoundException {
        private String message;
    }

    @Data
    @AllArgsConstructor
    private static class NoContentException {
        private String message;
    }
}