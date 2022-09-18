package com.spimex.purchasebonuses.exception;

public class NotSupportedTypeException extends RuntimeException {

    public final static String NOT_SUPPORTED_TEMPLATE = "Payment source '%s' does not supported";

    public NotSupportedTypeException(String message) {
        super(message);
    }
}
