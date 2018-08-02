package com.ml.vulferbetsystem.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpStatusCodeException;

public class StatusCodeException extends HttpStatusCodeException {
    private Exception exception;

    /**
     * Constructor que almacena la excepción originaria.
     * @param statusCode Código HTTP a devolver.
     * @param e          Excepcion originaria.
     */
    public StatusCodeException(HttpStatus statusCode, Exception e) {
        super(statusCode);
        this.exception = e;
    }
    /**
     * Constructor.
     * @param statusCode Código HTTP a devolver.
     */
    public StatusCodeException(HttpStatus statusCode,String message) {
        super(statusCode,message);
        this.exception = null;
    }
}
