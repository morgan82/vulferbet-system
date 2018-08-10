package com.ml.vulferbetsystem.error;

import org.springframework.http.HttpStatus;

public class StatusCodeException extends RuntimeException {
    private Exception exception;
    private HttpStatus statusCode;

    /**
     * Constructor que almacena la excepción originaria.
     * @param statusCode Código HTTP a devolver.
     * @param e          Excepcion originaria.
     */
    public StatusCodeException(HttpStatus statusCode, Exception e) {
        this.statusCode = statusCode;
        this.exception = e;
    }
    /**
     * Constructor.
     * @param statusCode Código HTTP a devolver.
     */
    public StatusCodeException(HttpStatus statusCode,String message) {
        super(message);
        this.statusCode = statusCode;
        this.exception = null;
    }

    public String getStatusText() {
        return this.getMessage();
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
