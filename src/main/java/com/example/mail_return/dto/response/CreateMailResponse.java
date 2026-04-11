package com.example.mail_return.dto.response;

import com.example.mail_return.entity.MailEntity;
import java.time.LocalDateTime;

public class CreateMailResponse {

    private Long id;
    private String subject;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CreateMailResponse() {
    }

    public CreateMailResponse(Long id, String subject, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CreateMailResponse from(MailEntity entity) {
        return new CreateMailResponse(
                entity.getId(),
                entity.getSubject(),
                entity.getMessage(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public Long getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
