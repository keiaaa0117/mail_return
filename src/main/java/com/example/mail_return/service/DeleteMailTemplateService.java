package com.example.mail_return.service;

import com.example.mail_return.entity.MailTemplateEntity;
import com.example.mail_return.exception.MailTemplateNotFoundException;
import com.example.mail_return.repository.MailTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMailTemplateService {

    private final MailTemplateRepository mailTemplateRepository;

    public DeleteMailTemplateService(MailTemplateRepository mailTemplateRepository) {
        this.mailTemplateRepository = mailTemplateRepository;
    }

    @Transactional
    public MailTemplateEntity logicalDelete(Long templateId) {
        MailTemplateEntity template = mailTemplateRepository.findById(templateId)
                .orElseThrow(() -> new MailTemplateNotFoundException(templateId));

        template.setActive(false);
        return mailTemplateRepository.save(template);
    }
}
