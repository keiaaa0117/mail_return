package com.example.mail_return.service;

import com.example.mail_return.entity.MailEntity;
import com.example.mail_return.repository.MailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateMailService {

    private final MailRepository mailRepository;

    public CreateMailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    /**
     * メールを作成
     */
    @Transactional
    public MailEntity createMail(String subject, String message) {
        MailEntity mail = new MailEntity(subject, message);
        return mailRepository.save(mail);
    }
}
