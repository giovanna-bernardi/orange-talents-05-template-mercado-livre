package br.com.zupacademy.giovanna.mercadolivre.handler;

import br.com.zupacademy.giovanna.mercadolivre.exception.ValidationExceptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError :: getField).collect(Collectors.joining(", "));

        StringBuilder errors = new StringBuilder();
        globalErrors.forEach(error -> errors.append(getErrorMessage(error)).append(". "));
        fieldErrors.forEach(error -> errors.append(getErrorMessage(error)).append(". "));

        String messages = errors.toString();

        String title = "Erro de validação";
        String details = "Verifique a mensagem de erro no campo 'fieldsMessage'";
        LocalDateTime timestamp = LocalDateTime.now();
        String developerMessage = exception.getClass().getName();

        return new ResponseEntity<>(
                new ValidationExceptionDetails(title,status.value(),details,developerMessage,timestamp,
                        fields,messages),
                HttpStatus.BAD_REQUEST);
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
