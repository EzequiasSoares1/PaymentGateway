package com.gateway.paymentgateway.exception;

public class NotAuthorizeException extends RuntimeException{
    public NotAuthorizeException(String message) {
        super(message);
    }
}