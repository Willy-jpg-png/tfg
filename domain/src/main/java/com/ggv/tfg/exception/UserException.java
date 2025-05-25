package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class UserException extends Exception {
    public UserException(final String message) {
        super(message);
    }
}
