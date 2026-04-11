package com.example.mail_return.dto.response;

public class UpdateMailResponse {

    private Long id;
    private String subject;
    private String message;
    private boolean active;

    public UpdateMailResponse() {
    }

    public UpdateMailResponse(Long id, String subject, String message, boolean active) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.active = active;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}