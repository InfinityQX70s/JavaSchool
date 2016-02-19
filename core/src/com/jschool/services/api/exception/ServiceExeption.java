package com.jschool.services.api.exception;

/**
 * Created by infinity on 18.02.16.
 */
public class ServiceExeption extends Exception{

    private StatusCode statusCode;

    public ServiceExeption(String message, StatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServiceExeption(String message, Throwable cause, StatusCode statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }
}
