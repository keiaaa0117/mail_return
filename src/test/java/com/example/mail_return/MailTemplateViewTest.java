package com.example.mail_return;

import com.example.mail_return.entity.MailTemplateEntity;
import com.example.mail_return.repository.MailTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MailTemplateViewTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MailTemplateRepository mailTemplateRepository;

    @BeforeEach
    void setUp() {
        mailTemplateRepository.deleteAll();
    }

    @Test
    @DisplayName("Mail top screen has template list link")
    void mailTop_hasTemplateListLink() throws Exception {
        mockMvc.perform(get("/mail/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("/mail/template/list")));
    }

    @Test
    @DisplayName("Mail create screen has active template selector")
    void mailCreate_hasActiveTemplateSelector() throws Exception {
        mailTemplateRepository.save(new MailTemplateEntity("Create mail template", "Template for create mail"));

        MailTemplateEntity inactiveTemplate = new MailTemplateEntity("Inactive name", "Inactive template");
        inactiveTemplate.setActive(false);
        mailTemplateRepository.save(inactiveTemplate);

        mockMvc.perform(get("/mail/create"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("templateSelect")))
                .andExpect(content().string(containsString("Create mail template")))
                .andExpect(content().string(containsString("/js/mail-template.js")))
                .andExpect(content().string(not(containsString("Inactive name"))));
    }

    @Test
    @DisplayName("Mail template list screen shows active templates")
    void templateList_showsActiveTemplates() throws Exception {
        mailTemplateRepository.save(new MailTemplateEntity("Active name", "Active template"));

        MailTemplateEntity inactiveTemplate = new MailTemplateEntity("Inactive name", "Inactive template");
        inactiveTemplate.setActive(false);
        mailTemplateRepository.save(inactiveTemplate);

        mockMvc.perform(get("/mail/template/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Active name")))
                .andExpect(content().string(containsString("Active template")))
                .andExpect(content().string(not(containsString("Inactive name"))));
    }

    @Test
    @DisplayName("Mail template create screen creates template")
    void createTemplateScreen_success() throws Exception {
        mockMvc.perform(post("/mail/template/create")
                .param("templateName", "Created name")
                .param("templateText", "Created from screen"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mail/template/list"));

        mockMvc.perform(get("/mail/template/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Created name")))
                .andExpect(content().string(containsString("Created from screen")));
    }

    @Test
    @DisplayName("Mail template edit screen updates template")
    void editTemplateScreen_success() throws Exception {
        MailTemplateEntity template = mailTemplateRepository.save(
                new MailTemplateEntity("Before name", "Before update"));

        mockMvc.perform(get("/mail/template/edit/{templateId}", template.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Before name")))
                .andExpect(content().string(containsString("Before update")));

        mockMvc.perform(post("/mail/template/edit/{templateId}", template.getId())
                .param("templateName", "After name")
                .param("templateText", "After update"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mail/template/list"));

        mockMvc.perform(get("/mail/template/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("After name")))
                .andExpect(content().string(containsString("After update")));
    }

    @Test
    @DisplayName("Mail template delete screen logically deletes template")
    void deleteTemplateScreen_success() throws Exception {
        MailTemplateEntity template = mailTemplateRepository.save(
                new MailTemplateEntity("Delete name", "Delete from screen"));

        mockMvc.perform(post("/mail/template/delete/{templateId}", template.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mail/template/list"));

        mockMvc.perform(get("/mail/template/list"))
                .andExpect(status().isOk())
                .andExpect(content().string(not(containsString("Delete from screen"))));
    }
}
