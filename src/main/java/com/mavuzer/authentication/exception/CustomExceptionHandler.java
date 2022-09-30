package com.mavuzer.authentication.exception;

import com.mavuzer.authentication.exception.dto.ExceptionResponse;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage(),LocalDateTime.now() ,request.getDescription(true));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<Object> handleAllUserNotFoundException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage(),LocalDateTime.now() ,request.getDescription(true));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExist.class)
    public final ResponseEntity<Object> handleAllUserAlreadyExistException(Exception ex, WebRequest request) {

        ExceptionResponse exceptionResponse =
                new ExceptionResponse(HttpStatus.CONFLICT, ex.getMessage(),LocalDateTime.now() ,request.getDescription(true));

        return new ResponseEntity<>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        List<ExceptionResponse> exceptionResponses = errors.stream()
                .map(error -> new ExceptionResponse(HttpStatus.BAD_REQUEST, error.getDefaultMessage(),LocalDateTime.now() ,error.getField())).collect(Collectors.toList());

        return new ResponseEntity<>(exceptionResponses, HttpStatus.BAD_REQUEST);
    }


}
