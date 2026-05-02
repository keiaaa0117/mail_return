package com.example.mail_return.controller;

import com.example.mail_return.dto.request.UpdateMailRequest;
import com.example.mail_return.entity.MailEntity;
import com.example.mail_return.form.MailForm;
import com.example.mail_return.repository.MailRepository;
import com.example.mail_return.service.CreateMailService;
import com.example.mail_return.service.DeleteMailService;
import com.example.mail_return.service.GetMailListService;
import com.example.mail_return.service.GetMailTemplateListService;
import com.example.mail_return.service.UpdateMailService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class ViewController {

    private final GetMailListService getMailListService;
    private final CreateMailService createMailService;
    private final UpdateMailService updateMailService;
    private final DeleteMailService deleteMailService;
    private final MailRepository mailRepository;
    private final GetMailTemplateListService getMailTemplateListService;

    public ViewController(
            GetMailListService getMailListService,
            CreateMailService createMailService,
            UpdateMailService updateMailService,
            DeleteMailService deleteMailService,
            MailRepository mailRepository,
            GetMailTemplateListService getMailTemplateListService) {
        this.getMailListService = getMailListService;
        this.createMailService = createMailService;
        this.updateMailService = updateMailService;
        this.deleteMailService = deleteMailService;
        this.mailRepository = mailRepository;
        this.getMailTemplateListService = getMailTemplateListService;
    }

    @GetMapping({"/", "/mail/list"})
    public String index(Model model) {
        model.addAttribute("mailList", getMailListService.getMailList());
        return "mail_top";
    }

    @GetMapping("/mail/create")
    public String showCreateForm(Model model) {
        model.addAttribute("mailForm", new MailForm());
        model.addAttribute("templateList", getMailTemplateListService.getActiveTemplates());
        return "mail_add";
    }

    @PostMapping("/mail/create")
    public String create(@ModelAttribute MailForm mailForm) {
        createMailService.createMail(mailForm.getSubject(), mailForm.getMessage());
        return "redirect:/mail/list";
    }

    @GetMapping("/mail/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MailEntity mail = mailRepository.findById(id)
                .filter(MailEntity::getActive)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        model.addAttribute("mailForm", MailForm.from(mail));
        return "mail_edit";
    }

    @PostMapping("/mail/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute MailForm mailForm) {
        UpdateMailRequest request = new UpdateMailRequest(mailForm.getSubject(), mailForm.getMessage());
        updateMailService.update(id, request);
        return "redirect:/mail/list";
    }

    @PostMapping("/mail/delete/{id}")
    public String delete(@PathVariable Long id) {
        deleteMailService.logicalDelete(id);
        return "redirect:/mail/list";
    }
}
