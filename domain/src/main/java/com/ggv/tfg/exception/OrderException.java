package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class OrderException extends Exception {
    public OrderException(final String message) {
        super(message);
    }
}
