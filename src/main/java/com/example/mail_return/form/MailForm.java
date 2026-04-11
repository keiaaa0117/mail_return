package com.example.mail_return.form;

import com.example.mail_return.entity.MailEntity;

public class MailForm {

    private Long id;
    private String subject;
    private String message;

    public static MailForm from(MailEntity entity) {
        MailForm form = new MailForm();
        form.setId(entity.getId());
        form.setSubject(entity.getSubject());
        form.setMessage(entity.getMessage());
        return form;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
