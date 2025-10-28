package com.seibel.basicspring.common.exceptions;

public class ServiceException extends BaseServiceException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}