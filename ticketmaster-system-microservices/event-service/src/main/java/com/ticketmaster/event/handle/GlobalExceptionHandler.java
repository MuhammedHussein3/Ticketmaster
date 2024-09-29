package com.ticketmaster.event.handle;

import com.ticketmaster.event.exceptions.EventCreationException;
import com.ticketmaster.event.exceptions.EventDeletionException;
import com.ticketmaster.event.exceptions.InvalidEventUpdateException;
import com.ticketmaster.event.exceptions.EventNotFoundException;
import com.ticketmaster.event.exceptions.VenueNotFoundException;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFoundException(EventNotFoundException e) {
        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(VenueNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleVenueNotFoundException(VenueNotFoundException e) {
        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(NotFoundException e) {
        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(EventCreationException.class)
    public ResponseEntity<ErrorResponse> handleEventCreationException(EventCreationException e) {
        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(EventDeletionException.class)
    public ResponseEntity<ErrorResponse> handleEventDeletionException(EventDeletionException e) {
        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(InvalidEventUpdateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEventUpdateException(InvalidEventUpdateException e) {
        var errors = new HashMap<String, String>();
        errors.put("message", e.getMessage());
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errors.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        log.error("Unhandled exception occurred: ", exp);
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }
}
