package com.example.mail_return.exception;

public class InactiveMailTemplateException extends RuntimeException {

    public InactiveMailTemplateException(Long templateId) {
        super("Mail template is inactive. templateId=" + templateId);
    }
}
