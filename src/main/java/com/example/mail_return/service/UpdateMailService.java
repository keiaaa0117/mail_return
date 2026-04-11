package com.example.mail_return.service;

import com.example.mail_return.dto.request.UpdateMailRequest;
import com.example.mail_return.dto.response.UpdateMailResponse;
import com.example.mail_return.entity.MailEntity;
import com.example.mail_return.repository.MailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateMailService {

    private final MailRepository mailRepository;

    public UpdateMailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    @Transactional
    public UpdateMailResponse update(Long id, UpdateMailRequest request) {
        MailEntity mail = mailRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("対象のメールが存在しません。id=" + id));

        if (!mail.getActive()) {
            throw new IllegalStateException("論理削除済みのメールは編集できません。id=" + id);
        }

        mail.setSubject(request.getSubject());
        mail.setMessage(request.getMessage());

        MailEntity updatedMail = mailRepository.save(mail);

        return new UpdateMailResponse(
                updatedMail.getId(),
                updatedMail.getSubject(),
                updatedMail.getMessage(),
                updatedMail.getActive());
    }
}