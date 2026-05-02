package com.example.mail_return.controller;

import com.example.mail_return.exception.InactiveMailTemplateException;
import com.example.mail_return.exception.MailTemplateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = MailTemplateController.class)
public class MailTemplateExceptionHandler {

    @ExceptionHandler(MailTemplateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {
    }

    @ExceptionHandler(InactiveMailTemplateException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public void handleInactive() {
    }
}
