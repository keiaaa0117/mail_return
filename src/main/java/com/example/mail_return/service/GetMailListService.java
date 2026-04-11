package com.example.mail_return.service;

import com.example.mail_return.dto.response.GetMailListResponse;
import com.example.mail_return.entity.MailEntity;
import com.example.mail_return.repository.MailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetMailListService {

    private final MailRepository mailRepository;

    public GetMailListService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public List<GetMailListResponse> getMailList() {
        List<MailEntity> mails = mailRepository.findByActiveTrue();

        return mails.stream()
                .map(mail -> new GetMailListResponse(
                        mail.getId(),
                        mail.getSubject(),
                        mail.getMessage(),
                        mail.getActive()))
                .collect(Collectors.toList());
    }
}