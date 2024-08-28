package com.ant.springpracticeguru.controller;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorController {

    @ExceptionHandler(TransactionSystemException.class)
    ResponseEntity handleJpaViolationException(TransactionSystemException exception){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();
        if(exception.getCause().getCause() instanceof ConstraintViolationException){
            ConstraintViolationException cve = (ConstraintViolationException) exception.getCause().getCause();
            List listErrors = cve.getConstraintViolations()
                    .stream()
                    .map(constraintViolation -> {
                        Map<String,String> errorMap = new HashMap<>();
                        errorMap.put(constraintViolation.getPropertyPath().toString(),
                                     constraintViolation.getMessage());
                        return errorMap;
                    }).collect(Collectors.toList());
            return responseEntity.body(listErrors);

        }
        return responseEntity.build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindingError(MethodArgumentNotValidException exception){
        List errorList = exception.getFieldErrors()
                .stream()
                .map( fieldError -> {
                    Map<String,String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
                    return errorMap;
                }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }
}
