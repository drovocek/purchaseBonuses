package com.spimex.purchasebonuses.exception;

public class NotEnoughMoneyException extends RuntimeException {

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
