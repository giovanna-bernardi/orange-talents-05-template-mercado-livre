package br.com.zupacademy.giovanna.mercadolivre.handler;

public class ValidationErrorsResponse {

    private String field;
    private String errorMessage;

    public ValidationErrorsResponse(String field, String errorMessage) {
        this.field = field;
        this.errorMessage = errorMessage;
    }

    public String getField() {
        return field;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
