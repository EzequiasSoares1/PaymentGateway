package com.br.sysgateway.config;

import com.br.sysgateway.exceptions.PaymentFailedException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.sql.SQLException;
import java.sql.SQLTransientConnectionException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        String errorMessage = "No endpoint " + ex.getHttpMethod() + " " + ex.getRequestURL();
        log.warn(errorMessage, ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = "Request method '" + ex.getMethod() + "' is not supported for this endpoint";
        log.warn(message, ex);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(message);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        log.error("Runtime exception occurred", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            SQLTransientConnectionException.class,
            DataAccessException.class,
            DataIntegrityViolationException.class,
            ConstraintViolationException.class,
            EntityNotFoundException.class,
            SQLException.class,
            CannotCreateTransactionException.class
    })
    public ResponseEntity<String> handleDatabaseException(Exception ex) {
        String errorMessage;
        if (ex instanceof SQLTransientConnectionException) {
            errorMessage = "Database connection error. Please try again later.";
            log.error(errorMessage, ex);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorMessage);
        } else if (ex instanceof DataIntegrityViolationException) {
            errorMessage = "Database integrity violation. Please check your data.";
            log.warn(errorMessage, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else if (ex instanceof ConstraintViolationException) {
            errorMessage = "Database constraint violation. Please check your data.";
            log.warn(errorMessage, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else if (ex instanceof SQLException) {
            errorMessage = "Error: " + ex.getMessage();
            log.error(errorMessage, ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else if (ex instanceof EntityNotFoundException) {
            errorMessage = "Entity not found.";
            log.warn(errorMessage, ex);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else {
            errorMessage = "Database error. Please try again later.";
            log.error(errorMessage, ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @ExceptionHandler(PaymentFailedException.class)
    public ResponseEntity<String> paymentFailedException(PaymentFailedException ex) {
        log.error("Gateway error", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro com a Instituição de pagamento, tente mais tarde!");
    }
}
