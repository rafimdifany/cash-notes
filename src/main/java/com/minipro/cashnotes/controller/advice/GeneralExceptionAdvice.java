package com.minipro.cashnotes.controller.advice;


import com.minipro.cashnotes.dto.advice.DefaultAttributeError;
import com.minipro.cashnotes.dto.advice.DetailError;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GeneralExceptionAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public DefaultAttributeError Exception(Exception ex, HttpServletRequest request) {
        DefaultAttributeError defaultError = DefaultAttributeError.builder()
                .timestamp(LocalDateTime.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getLocalizedMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .details(new ArrayList<>())
                .build();
        return defaultError;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public DefaultAttributeError BindException(BindException ex, HttpServletRequest request) {
        BindingResult result = ex.getBindingResult();
        List<ObjectError> objectErrorList = result.getAllErrors();

        List<String> propertyPaths = new ArrayList<>();
        List<DetailError> detailErrorList = objectErrorList.stream().map(error -> {
                    ConstraintViolationImpl<?> constraintViolation = error.unwrap(ConstraintViolationImpl.class);
                    String propertyPath = constraintViolation.getPropertyPath().toString();
                    propertyPaths.add(propertyPath);
                    return DetailError.builder()
                            .field(propertyPath)
                            .rejectedValue(constraintViolation.getInvalidValue())
                            .objectName(error.getObjectName())
                            .code(error.getCode())
                            .defaultMessage(error.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());

        DefaultAttributeError defaultError = DefaultAttributeError.builder()
                .timestamp(LocalDateTime.now().toString())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message("Validation failed for " + ex.getObjectName() + "(" + StringUtils.join(propertyPaths, ",") + ")" + ". Error count " + ex.getErrorCount())
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .details(detailErrorList)
                .build();
        return defaultError;
    }
}
