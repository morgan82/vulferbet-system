package com.ml.vulferbetsystem.error;

public class ErrorMessage {

    private String message;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage() {
    }

    //getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
