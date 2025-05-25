package com.ggv.tfg.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> internalServerError(final Exception exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ConstraintViolationException.class, SignUpException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> badRequest(final Exception exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ChangeSetPersister.NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> notFound(final Exception exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    public record ExceptionResponse(String message){}
}
