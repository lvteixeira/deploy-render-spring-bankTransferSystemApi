package com.acme.bankTransferSystemApi.exception;

public class DateToTransferNotAllowedException extends RuntimeException {
    public DateToTransferNotAllowedException(String message) {
        super(message);
    }
}
