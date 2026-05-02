package com.example.mail_return.service;

import com.example.mail_return.entity.MailTemplateEntity;
import com.example.mail_return.repository.MailTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateMailTemplateService {

    private final MailTemplateRepository mailTemplateRepository;

    public CreateMailTemplateService(MailTemplateRepository mailTemplateRepository) {
        this.mailTemplateRepository = mailTemplateRepository;
    }

    @Transactional
    public MailTemplateEntity create(String templateName, String templateText) {
        MailTemplateEntity template = new MailTemplateEntity(templateName, templateText);
        return mailTemplateRepository.save(template);
    }
}
