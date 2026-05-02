package com.example.mail_return.controller;

import com.example.mail_return.dto.request.MailTemplateRequest;
import com.example.mail_return.dto.response.MailTemplateIdResponse;
import com.example.mail_return.dto.response.MailTemplateResponse;
import com.example.mail_return.entity.MailTemplateEntity;
import com.example.mail_return.service.CreateMailTemplateService;
import com.example.mail_return.service.DeleteMailTemplateService;
import com.example.mail_return.service.GetMailTemplateListService;
import com.example.mail_return.service.GetMailTemplateService;
import com.example.mail_return.service.UpdateMailTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mail/template")
public class MailTemplateController {

    private final CreateMailTemplateService createMailTemplateService;
    private final UpdateMailTemplateService updateMailTemplateService;
    private final DeleteMailTemplateService deleteMailTemplateService;
    private final GetMailTemplateListService getMailTemplateListService;
    private final GetMailTemplateService getMailTemplateService;

    public MailTemplateController(
            CreateMailTemplateService createMailTemplateService,
            UpdateMailTemplateService updateMailTemplateService,
            DeleteMailTemplateService deleteMailTemplateService,
            GetMailTemplateListService getMailTemplateListService,
            GetMailTemplateService getMailTemplateService) {
        this.createMailTemplateService = createMailTemplateService;
        this.updateMailTemplateService = updateMailTemplateService;
        this.deleteMailTemplateService = deleteMailTemplateService;
        this.getMailTemplateListService = getMailTemplateListService;
        this.getMailTemplateService = getMailTemplateService;
    }

    @PostMapping
    public ResponseEntity<MailTemplateIdResponse> create(@Valid @RequestBody MailTemplateRequest request) {
        MailTemplateEntity template = createMailTemplateService.create(
                request.getTemplateName(),
                request.getTemplateText());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MailTemplateIdResponse(template.getId()));
    }

    @PutMapping
    public MailTemplateIdResponse update(
            @RequestParam Long templateId,
            @Valid @RequestBody MailTemplateRequest request) {
        MailTemplateEntity template = updateMailTemplateService.update(templateId, request);
        return new MailTemplateIdResponse(template.getId());
    }

    @DeleteMapping
    public MailTemplateIdResponse delete(@RequestParam Long templateId) {
        MailTemplateEntity template = deleteMailTemplateService.logicalDelete(templateId);
        return new MailTemplateIdResponse(template.getId());
    }

    @GetMapping(params = "!templateId")
    public List<MailTemplateResponse> getActiveTemplates() {
        return getMailTemplateListService.getActiveTemplates();
    }

    @GetMapping(params = "templateId")
    public MailTemplateResponse getActiveTemplate(@RequestParam Long templateId) {
        return getMailTemplateService.getActiveTemplate(templateId);
    }
}
