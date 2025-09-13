package com.eventhub.shared.dto;

public class ErrorDetails {
    public int status;
    public String message;

    public ErrorDetails(int status, String message) {
        this.message = message;
        this.status = status;
    }
}
