package com.company.exceptions;

import com.company.model.response.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        List<String> errors = new LinkedList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        body.put("errors", errors);
        ApiResponse<Map<String, Object>> apiResponse = new ApiResponse<>("not valid", body, status.value());
        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<?> handleItemNotFoundException(RuntimeException ex) {
        ex.printStackTrace();
        log.error(ex.getMessage());
        return ResponseEntity.ok(new ApiResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse<>(e.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<?> handleBadRequestException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<?> handleUnauthorizedException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(e.getMessage(), HttpStatus.UNAUTHORIZED.value()));
    }
}
