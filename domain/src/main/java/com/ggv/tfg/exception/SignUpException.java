package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class SignUpException extends Exception {
    public SignUpException(final String message) {
        super(message);
    }
}
