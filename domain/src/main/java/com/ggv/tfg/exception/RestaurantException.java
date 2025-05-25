package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class RestaurantException extends ClassNotFoundException {
    public RestaurantException(final String message) {
        super(message);
    }
}
