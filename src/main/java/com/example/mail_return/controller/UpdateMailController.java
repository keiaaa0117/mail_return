package com.example.mail_return.controller;

import com.example.mail_return.dto.request.UpdateMailRequest;
import com.example.mail_return.dto.response.UpdateMailResponse;
import com.example.mail_return.service.UpdateMailService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class UpdateMailController {

    private final UpdateMailService updateMailService;

    public UpdateMailController(UpdateMailService updateMailService) {
        this.updateMailService = updateMailService;
    }

    @PutMapping("/{id}")
    public UpdateMailResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMailRequest request) {
        return updateMailService.update(id, request);
    }
}