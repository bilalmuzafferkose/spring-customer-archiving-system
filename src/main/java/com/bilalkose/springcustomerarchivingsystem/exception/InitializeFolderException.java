package com.bilalkose.springcustomerarchivingsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InitializeFolderException extends RuntimeException {
    public InitializeFolderException(String message) {
        super(message);
    }
}
