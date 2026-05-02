package com.example.mail_return.exception;

public class MailTemplateNotFoundException extends RuntimeException {

    public MailTemplateNotFoundException(Long templateId) {
        super("Mail template is not found. templateId=" + templateId);
    }
}
