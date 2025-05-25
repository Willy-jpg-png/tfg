package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class AddingException extends ClassNotFoundException {
    public AddingException(final String message) {
        super(message);
    }
}
