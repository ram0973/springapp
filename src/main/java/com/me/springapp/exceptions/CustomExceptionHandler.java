package com.me.springapp.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    @NonNull
    protected ResponseEntity<Object> handleExceptionInternal(
        @NonNull Exception ex, @Nullable Object body, @NonNull HttpHeaders headers, HttpStatus status,
        @NonNull WebRequest request) {
        if (status.is5xxServerError()) {
            log.error("An 5xx exception {} occurred, which will cause a {} response", ex.getLocalizedMessage(), status);
        } else if (status.is4xxClientError()) {
            log.warn("An 4xx exception {} occurred, which will cause a {} response", ex.getLocalizedMessage(), status);
        } else {
            log.debug("An exception {} occurred, which will cause a {} response", ex.getLocalizedMessage(), status);
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
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

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    protected ResponseEntity<BadRequestException> handleUsernameAlreadyTakenException() {
        return new ResponseEntity<>(new BadRequestException("Username already taken"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    protected ResponseEntity<BadRequestException> handleEmailAlreadyInUseException() {
        return new ResponseEntity<>(new BadRequestException("Email already in use"), HttpStatus.BAD_REQUEST);
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

    @Data
    @AllArgsConstructor
    private static class BadRequestException {
        private String message;
    }
}