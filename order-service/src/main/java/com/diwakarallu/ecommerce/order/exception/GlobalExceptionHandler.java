package com.diwakarallu.ecommerce.order.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import jakarta.validation.ConstraintViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(InventoryUnavailableException.class)
        public ResponseEntity<ApiError> handleInventoryUnavailable(InventoryUnavailableException ex,
                        WebRequest request) {
                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
        }

        @ExceptionHandler(ProductOutOfStockException.class)
        public ResponseEntity<ApiError> handleProductOutOfStock(ProductOutOfStockException ex, WebRequest request) {
                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.CONFLICT.value())
                                .error(HttpStatus.CONFLICT.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {

                var fieldErrors = ex.getBindingResult().getFieldErrors()
                                .stream()
                                .collect(Collectors.toMap(
                                                err -> err.getField(),
                                                err -> err.getDefaultMessage()));

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .message("Validation failed for request")
                                .path(request.getDescription(false).replace("uri=", ""))
                                .fieldErrors(fieldErrors)
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

                var violations = ex.getConstraintViolations()
                                .stream()
                                .collect(Collectors.toMap(
                                                v -> v.getPropertyPath().toString(),
                                                v -> v.getMessage()));

                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.BAD_REQUEST.value())
                                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .message("Validation failed for request parameters")
                                .path(request.getDescription(false).replace("uri=", ""))
                                .fieldErrors(violations)
                                .build();

                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
                ApiError error = ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getDescription(false).replace("uri=", ""))
                                .build();

                return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
