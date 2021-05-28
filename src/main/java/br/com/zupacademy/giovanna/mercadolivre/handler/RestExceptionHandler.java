package br.com.zupacademy.giovanna.mercadolivre.handler;

import br.com.zupacademy.giovanna.mercadolivre.exception.ValidationExceptionDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<?> handlerException(MethodArgumentNotValidException exception) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        ValidationExceptionDetails validationDetails = buildValidationErrors(globalErrors, fieldErrors, exception);

        return ResponseEntity.badRequest().body(validationDetails);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ValidationExceptionDetails handleValidationError(BindException exception) {

        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationErrors(globalErrors, fieldErrors, exception);
    }

    private ValidationExceptionDetails buildValidationErrors(List<ObjectError> globalErrors, List<FieldError> fieldErrors, Exception exception){
        String fields = fieldErrors.stream().map(FieldError :: getField).collect(Collectors.joining(", "));

        StringBuilder errors = new StringBuilder();
        globalErrors.forEach(error -> errors.append(getErrorMessage(error)).append(". "));
        fieldErrors.forEach(error -> errors.append(getErrorMessage(error)).append(". "));

        String messages = errors.toString();

        String title = "Erro de validação";
        String details = "Verifique a mensagem de erro no campo 'fieldsMessage'";
        LocalDateTime timestamp = LocalDateTime.now();
        String developerMessage = exception.getClass().getName();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ValidationExceptionDetails(title,status.value(),details,
                        developerMessage,timestamp, fields, messages);
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}
