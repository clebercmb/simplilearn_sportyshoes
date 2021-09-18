package com.sportyshoes.share.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SportyShoesResourceAlreadyExistException extends RuntimeException {
    public SportyShoesResourceAlreadyExistException() {
        super();
    }
    public SportyShoesResourceAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
    public SportyShoesResourceAlreadyExistException(String message) {
        super(message);
    }
    public SportyShoesResourceAlreadyExistException(Throwable cause) {
        super(cause);
    }
}