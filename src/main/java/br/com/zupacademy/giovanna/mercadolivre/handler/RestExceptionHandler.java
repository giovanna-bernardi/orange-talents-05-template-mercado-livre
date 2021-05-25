package br.com.zupacademy.giovanna.mercadolivre.handler;

import br.com.zupacademy.giovanna.mercadolivre.exception.ValidationExceptionDetails;
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
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        String fields = fieldErrors.stream().map(FieldError :: getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError :: getDefaultMessage).collect(Collectors.joining(". "));

        String title = "Erro de validação";
        String details = "Verifique a mensagem de erro no campo 'fieldsMessage'";
        LocalDateTime timestamp = LocalDateTime.now();
        String developerMessage = exception.getClass().getName();

        return new ResponseEntity<>(
                new ValidationExceptionDetails(title,status.value(),details,developerMessage,timestamp,
                        fields,fieldsMessage),
                HttpStatus.BAD_REQUEST);
    }
}
