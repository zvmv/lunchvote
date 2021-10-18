package ru.pet.lunchvote.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @Getter
    public class ValidationErrorResponse {
        private final List<Violation> violations;

        public ValidationErrorResponse(List<Violation> violations) {
            this.violations = violations;
        }
    }

    @Getter
    public class Violation {
        private final String fieldName;
        private final String message;

        public Violation(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }
    }

    @ResponseBody
    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onHibernateConstraintViolationException(org.hibernate.exception.ConstraintViolationException e){
        final List<Violation> violations = Arrays.asList(
                new Violation("constraint: " + e.getConstraintName(), e.getMessage()));
        return new ValidationErrorResponse(violations);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintViolationException(ConstraintViolationException e){
        final List<Violation> violations = e.getConstraintViolations().stream().map(
                v -> new Violation(v.getPropertyPath().toString(), v.getMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}
