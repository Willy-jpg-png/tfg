package com.ggv.tfg.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessage {

    USER_EXISTS_EXCEPTION("The user already exists"),
    INVALID_CREDENTIALS_EXCEPTION("Invalid credentials"),
    UPDATE_USER_EXCEPTION("Unexpected error while updating user"),
    SIGN_UP_EXCEPTION("Unexpected error while signing up"),
    CREATE_PRODUCT_EXCEPTION("Unexpected error while creating product"),
    RESTAURANT_NOT_FOUND_EXCEPTION("Restaurant not found"),
    ADDING_NOT_FOUND_EXCEPTION("Adding not found"),
    PRODUCT_NOT_FOUND_EXCEPTION("Product not found"),
    ORDER_NOT_FOUND_EXCEPTION("Order not found"),
    PRODUCT_UPDATE_EXCEPTION("Unexpected error while updating product"),
    ADDING_UPDATE_EXCEPTION("Unexpected error while updating adding"),
    PRODUCT_DELETE_EXCEPTION("Unexpected error while deleting product"),
    ADDING_DELETE_EXCEPTION("Unexpected error while deleting adding"),
    CREATE_ORDER_EXCEPTION("Unexpected error while creating order"),
    ORDER_DELETE_EXCEPTION("Unexpected error while deleting order"),
    RETRIEVE_RESTAURANT_EXCEPTION("Unexpected error while retrieving restaurant"),
    RETRIEVE_PRODUCTS_EXCEPTION("Unexpected error while retrieving products"),
    RETRIEVE_ADDINGS_EXCEPTION("Unexpected error while retrieving addings");

    private final String message;
}
