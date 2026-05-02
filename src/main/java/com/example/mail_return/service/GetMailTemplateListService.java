package com.example.mail_return.service;

import com.example.mail_return.dto.response.MailTemplateResponse;
import com.example.mail_return.repository.MailTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetMailTemplateListService {

    private final MailTemplateRepository mailTemplateRepository;

    public GetMailTemplateListService(MailTemplateRepository mailTemplateRepository) {
        this.mailTemplateRepository = mailTemplateRepository;
    }

    public List<MailTemplateResponse> getActiveTemplates() {
        return mailTemplateRepository.findByActiveTrue().stream()
                .map(MailTemplateResponse::from)
                .collect(Collectors.toList());
    }
}
