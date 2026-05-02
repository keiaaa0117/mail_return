package com.example.mail_return.dto.response;

import com.example.mail_return.entity.MailTemplateEntity;

public class MailTemplateResponse {

    private Long templateId;
    private String templateName;
    private String templateText;

    public MailTemplateResponse() {
    }

    public MailTemplateResponse(Long templateId, String templateName, String templateText) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.templateText = templateText;
    }

    public static MailTemplateResponse from(MailTemplateEntity entity) {
        return new MailTemplateResponse(entity.getId(), entity.getTemplateName(), entity.getTemplateText());
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateText() {
        return templateText;
    }

    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }
}
