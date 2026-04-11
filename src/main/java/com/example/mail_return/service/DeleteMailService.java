package com.example.mail_return.service;

import com.example.mail_return.entity.MailEntity;
import com.example.mail_return.repository.MailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteMailService {

    private final MailRepository mailRepository;

    public DeleteMailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    @Transactional
    public void logicalDelete(Long id) {
        MailEntity mail = mailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("対象データが存在しません"));

        mail.setActive(false);

        mailRepository.save(mail);
    }
}
