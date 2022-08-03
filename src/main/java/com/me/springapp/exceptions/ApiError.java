package com.me.springapp.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class ApiError {
    public String url;
    public String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    public LocalDateTime timestamp;
    public HttpStatus status;

    public ApiError(HttpServletRequest req, Exception e, HttpStatus status) {
        this.url = req.getRequestURL().toString();
        this.message = e.getLocalizedMessage();
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }
}
