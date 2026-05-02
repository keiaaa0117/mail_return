package com.example.mail_return.dto.request;

import jakarta.validation.constraints.NotBlank;

public class MailTemplateRequest {

    @NotBlank(message = "templateName is required")
    private String templateName;

    @NotBlank(message = "templateText is required")
    private String templateText;

    public MailTemplateRequest() {
    }

    public MailTemplateRequest(String templateName, String templateText) {
        this.templateName = templateName;
        this.templateText = templateText;
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
