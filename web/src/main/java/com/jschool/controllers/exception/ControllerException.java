package com.jschool.controllers.exception;

/**
 * Created by infinity on 19.02.16.
 */
public class ControllerException extends Exception {

    private ControllerStatusCode controllerStatusCode;

    public ControllerException(String message, ControllerStatusCode controllerStatusCode) {
        super(message);
        this.controllerStatusCode = controllerStatusCode;
    }

    public ControllerException(String message, Throwable cause, ControllerStatusCode controllerStatusCode) {
        super(message, cause);
        this.controllerStatusCode = controllerStatusCode;
    }

    public ControllerStatusCode getStatusCode() {
        return controllerStatusCode;
    }
}
