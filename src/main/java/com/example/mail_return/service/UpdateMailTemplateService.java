package com.example.mail_return.service;

import com.example.mail_return.dto.request.MailTemplateRequest;
import com.example.mail_return.entity.MailTemplateEntity;
import com.example.mail_return.exception.InactiveMailTemplateException;
import com.example.mail_return.exception.MailTemplateNotFoundException;
import com.example.mail_return.repository.MailTemplateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMailTemplateService {

    private final MailTemplateRepository mailTemplateRepository;

    public UpdateMailTemplateService(MailTemplateRepository mailTemplateRepository) {
        this.mailTemplateRepository = mailTemplateRepository;
    }

    @Transactional
    public MailTemplateEntity update(Long templateId, MailTemplateRequest request) {
        MailTemplateEntity template = mailTemplateRepository.findById(templateId)
                .orElseThrow(() -> new MailTemplateNotFoundException(templateId));

        if (!template.getActive()) {
            throw new InactiveMailTemplateException(templateId);
        }

        template.setTemplateName(request.getTemplateName());
        template.setTemplateText(request.getTemplateText());
        return mailTemplateRepository.save(template);
    }
}
