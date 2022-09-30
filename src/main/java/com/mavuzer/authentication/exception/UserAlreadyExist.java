package com.mavuzer.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExist extends RuntimeException {
    public UserAlreadyExist() {
    }

    public UserAlreadyExist(String message) {
        super(message);
    }

    public UserAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExist(Throwable cause) {
        super(cause);
    }

    public UserAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
