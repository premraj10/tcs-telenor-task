package com.premraj.product.exception;

public class EmptyProductException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public EmptyProductException(){}
    public EmptyProductException(String message) {
        super(message);
    }
    public EmptyProductException(String message, Throwable error) {
        super(message, error);
    }

}
