package com.sportyshoes.share;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SportyShoesResourceNotFoundException extends RuntimeException {
    public SportyShoesResourceNotFoundException() {
        super();
    }
    public SportyShoesResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public SportyShoesResourceNotFoundException(String message) {
        super(message);
    }
    public SportyShoesResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}