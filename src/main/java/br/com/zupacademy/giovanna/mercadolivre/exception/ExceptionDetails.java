package br.com.zupacademy.giovanna.mercadolivre.exception;

import java.time.LocalDateTime;

public class ExceptionDetails {
    protected String title;
    protected int status;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;

    public ExceptionDetails(String title, int status, String details, String developerMessage, LocalDateTime timestamp) {
        this.title = title;
        this.status = status;
        this.details = details;
        this.developerMessage = developerMessage;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public int getStatus() {
        return status;
    }

    public String getDetails() {
        return details;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
