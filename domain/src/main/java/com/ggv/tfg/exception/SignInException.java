package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class SignInException extends Exception {
    public SignInException(final String message) {
        super(message);
    }
}
