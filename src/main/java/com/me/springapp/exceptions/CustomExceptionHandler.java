package com.me.springapp.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
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

    @ExceptionHandler(WrongEmailOrPasswordException.class)
    protected ResponseEntity<BadCredentialsException> handleWrongEmailOrPasswordException() {
        return new ResponseEntity<>(new BadCredentialsException("Bad credentials"),
            HttpStatus.UNAUTHORIZED);
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

    private record BadCredentialsException(String message) {

    }

    private record NotFoundException(String message) {
    }

    private record NoContentException(String message) {
    }

    private record BadRequestException(String message) {
    }
}