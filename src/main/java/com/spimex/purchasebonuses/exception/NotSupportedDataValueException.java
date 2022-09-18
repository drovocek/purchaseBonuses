package com.spimex.purchasebonuses.exception;

public class NotSupportedDataValueException extends RuntimeException {

    public final static String NOT_SUPPORTED_DATA_TEMPLATE = "Data value '%s' does not supported";

    public NotSupportedDataValueException(String message) {
        super(message);
    }
}
