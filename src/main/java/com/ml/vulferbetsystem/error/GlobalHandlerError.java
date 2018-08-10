package com.ml.vulferbetsystem.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalHandlerError {
    private static final Logger log = LoggerFactory.getLogger(GlobalHandlerError.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ErrorMessage globalHandler(Exception e) {
        log.error("500 in advice ", e);
        return new ErrorMessage(e.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalArgumentException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class})
    public ErrorMessage globalHandlerBadRequest(Exception e) {
        log.error("400 in advice ", e);
        return new ErrorMessage(e.getMessage());
    }

    @ExceptionHandler(StatusCodeException.class)
    public ResponseEntity<ErrorMessage> handleStatusCodeException(StatusCodeException e) {
        log.error("{} in advice ", e.getStatusCode());
        log.error("", e);
        return new ResponseEntity<>(new ErrorMessage(e.getStatusText()), e.getStatusCode());
    }

}

