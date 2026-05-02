package com.example.mail_return.dto.response;

public class MailTemplateIdResponse {

    private Long templateId;

    public MailTemplateIdResponse() {
    }

    public MailTemplateIdResponse(Long templateId) {
        this.templateId = templateId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
