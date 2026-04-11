package com.example.mail_return.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateMailRequest {

    @NotBlank(message = "件名は必須です。")
    @Size(max = 255, message = "件名は255文字以内で入力してください。")
    private String subject;

    @NotBlank(message = "本文は必須です。")
    private String message;

    public CreateMailRequest() {
    }

    public CreateMailRequest(String subject, String message) {
        this.subject = subject;
        this.message = message;
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