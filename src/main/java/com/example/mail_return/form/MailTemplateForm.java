package com.example.mail_return.form;

import com.example.mail_return.dto.response.MailTemplateResponse;

public class MailTemplateForm {

    private Long templateId;
    private String templateName;
    private String templateText;

    public static MailTemplateForm from(MailTemplateResponse response) {
        MailTemplateForm form = new MailTemplateForm();
        form.setTemplateId(response.getTemplateId());
        form.setTemplateName(response.getTemplateName());
        form.setTemplateText(response.getTemplateText());
        return form;
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
