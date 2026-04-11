package com.example.mail_return.controller;

import com.example.mail_return.dto.request.CreateMailRequest;
import com.example.mail_return.dto.response.CreateMailResponse;
import com.example.mail_return.service.CreateMailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class CreateMailController {

    private final CreateMailService createMailService;

    public CreateMailController(CreateMailService createMailService) {
        this.createMailService = createMailService;
    }

    /**
     * メールを作成
     * POST /api/mail
     */
    @PostMapping
    public ResponseEntity<CreateMailResponse> create(@Valid @RequestBody CreateMailRequest request) {
        CreateMailResponse response = CreateMailResponse.from(
                createMailService.createMail(request.getSubject(), request.getMessage()));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}