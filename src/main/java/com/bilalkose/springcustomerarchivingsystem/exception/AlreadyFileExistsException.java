package com.bilalkose.springcustomerarchivingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyFileExistsException extends RuntimeException {
    public AlreadyFileExistsException(String message) {
        super(message);
    }
}
