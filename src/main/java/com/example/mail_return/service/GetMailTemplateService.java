package com.example.mail_return.service;

import com.example.mail_return.dto.response.MailTemplateResponse;
import com.example.mail_return.entity.MailTemplateEntity;
import com.example.mail_return.exception.InactiveMailTemplateException;
import com.example.mail_return.exception.MailTemplateNotFoundException;
import com.example.mail_return.repository.MailTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class GetMailTemplateService {

    private final MailTemplateRepository mailTemplateRepository;

    public GetMailTemplateService(MailTemplateRepository mailTemplateRepository) {
        this.mailTemplateRepository = mailTemplateRepository;
    }

    public MailTemplateResponse getActiveTemplate(Long templateId) {
        MailTemplateEntity template = mailTemplateRepository.findById(templateId)
                .orElseThrow(() -> new MailTemplateNotFoundException(templateId));

        if (!template.getActive()) {
            throw new InactiveMailTemplateException(templateId);
        }

        return MailTemplateResponse.from(template);
    }
}
