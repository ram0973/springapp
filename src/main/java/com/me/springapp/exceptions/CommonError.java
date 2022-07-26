package com.me.springapp.exceptions;

import javax.servlet.http.HttpServletRequest;

public class CommonError {
    public final String url;
    public final String message;

    public CommonError(HttpServletRequest req, Exception e) {
        this.url = req.getRequestURL().toString();
        this.message = e.getLocalizedMessage();
    }
}
