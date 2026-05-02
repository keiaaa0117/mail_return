package com.example.mail_return.controller;

import com.example.mail_return.dto.request.MailTemplateRequest;
import com.example.mail_return.dto.response.MailTemplateResponse;
import com.example.mail_return.exception.InactiveMailTemplateException;
import com.example.mail_return.exception.MailTemplateNotFoundException;
import com.example.mail_return.form.MailTemplateForm;
import com.example.mail_return.service.CreateMailTemplateService;
import com.example.mail_return.service.DeleteMailTemplateService;
import com.example.mail_return.service.GetMailTemplateListService;
import com.example.mail_return.service.GetMailTemplateService;
import com.example.mail_return.service.UpdateMailTemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class MailTemplateViewController {

    private final GetMailTemplateListService getMailTemplateListService;
    private final GetMailTemplateService getMailTemplateService;
    private final CreateMailTemplateService createMailTemplateService;
    private final UpdateMailTemplateService updateMailTemplateService;
    private final DeleteMailTemplateService deleteMailTemplateService;

    public MailTemplateViewController(
            GetMailTemplateListService getMailTemplateListService,
            GetMailTemplateService getMailTemplateService,
            CreateMailTemplateService createMailTemplateService,
            UpdateMailTemplateService updateMailTemplateService,
            DeleteMailTemplateService deleteMailTemplateService) {
        this.getMailTemplateListService = getMailTemplateListService;
        this.getMailTemplateService = getMailTemplateService;
        this.createMailTemplateService = createMailTemplateService;
        this.updateMailTemplateService = updateMailTemplateService;
        this.deleteMailTemplateService = deleteMailTemplateService;
    }

    @GetMapping("/mail/template/list")
    public String index(Model model) {
        model.addAttribute("templateList", getMailTemplateListService.getActiveTemplates());
        return "mail_template_list";
    }

    @GetMapping("/mail/template/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mailTemplateForm", new MailTemplateForm());
        return "mail_template_add";
    }

    @PostMapping("/mail/template/create")
    public String create(@ModelAttribute MailTemplateForm mailTemplateForm) {
        createMailTemplateService.create(mailTemplateForm.getTemplateName(), mailTemplateForm.getTemplateText());
        return "redirect:/mail/template/list";
    }

    @GetMapping("/mail/template/edit/{templateId}")
    public String showEditForm(@PathVariable Long templateId, Model model) {
        MailTemplateResponse response = getTemplateOrThrowResponseStatus(templateId);
        model.addAttribute("mailTemplateForm", MailTemplateForm.from(response));
        return "mail_template_edit";
    }

    @PostMapping("/mail/template/edit/{templateId}")
    public String update(
            @PathVariable Long templateId,
            @ModelAttribute MailTemplateForm mailTemplateForm) {
        try {
            MailTemplateRequest request = new MailTemplateRequest(
                    mailTemplateForm.getTemplateName(),
                    mailTemplateForm.getTemplateText());
            updateMailTemplateService.update(templateId, request);
            return "redirect:/mail/template/list";
        } catch (MailTemplateNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        } catch (InactiveMailTemplateException exception) {
            throw new ResponseStatusException(HttpStatus.LOCKED, exception.getMessage(), exception);
        }
    }

    @PostMapping("/mail/template/delete/{templateId}")
    public String delete(@PathVariable Long templateId) {
        try {
            deleteMailTemplateService.logicalDelete(templateId);
            return "redirect:/mail/template/list";
        } catch (MailTemplateNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        }
    }

    private MailTemplateResponse getTemplateOrThrowResponseStatus(Long templateId) {
        try {
            return getMailTemplateService.getActiveTemplate(templateId);
        } catch (MailTemplateNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage(), exception);
        } catch (InactiveMailTemplateException exception) {
            throw new ResponseStatusException(HttpStatus.LOCKED, exception.getMessage(), exception);
        }
    }
}
