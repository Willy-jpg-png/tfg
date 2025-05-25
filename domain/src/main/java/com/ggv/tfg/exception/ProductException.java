package com.ggv.tfg.exception;

import lombok.Getter;

@Getter
public class ProductException extends Exception {
  public ProductException(final String message) {
    super(message);
  }
}
