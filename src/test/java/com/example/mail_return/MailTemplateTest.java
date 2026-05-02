package com.example.mail_return;

import com.example.mail_return.repository.MailTemplateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MailTemplateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MailTemplateRepository mailTemplateRepository;

    @BeforeEach
    void setUp() {
        mailTemplateRepository.deleteAll();
    }

    @Test
    @DisplayName("Mail template create API: success")
    void createMailTemplate_success() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("templateName", "Thanks template");
        requestBody.put("templateText", "Thank you for your message.");

        mockMvc.perform(post("/api/mail/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.templateId").exists());
    }

    @Test
    @DisplayName("Mail template update API: success")
    void updateMailTemplate_success() throws Exception {
        Long templateId = createTemplate("Before name", "Before update");

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("templateName", "After name");
        requestBody.put("templateText", "After update");

        mockMvc.perform(put("/api/mail/template")
                .queryParam("templateId", templateId.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.templateId").value(templateId));

        mockMvc.perform(get("/api/mail/template")
                .queryParam("templateId", templateId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.templateId").value(templateId))
                .andExpect(jsonPath("$.templateName").value("After name"))
                .andExpect(jsonPath("$.templateText").value("After update"));
    }

    @Test
    @DisplayName("Mail template delete API: logical delete")
    void deleteMailTemplate_success() throws Exception {
        Long templateId = createTemplate("Delete name", "Delete target");

        mockMvc.perform(delete("/api/mail/template")
                .queryParam("templateId", templateId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.templateId").value(templateId));

        mockMvc.perform(get("/api/mail/template")
                .queryParam("templateId", templateId.toString()))
                .andExpect(status().isLocked());
    }

    @Test
    @DisplayName("Mail template list API: only active templates")
    void getMailTemplateList_success() throws Exception {
        Long activeTemplateId = createTemplate("Active name", "Active template");
        Long inactiveTemplateId = createTemplate("Inactive name", "Inactive template");

        mockMvc.perform(delete("/api/mail/template")
                .queryParam("templateId", inactiveTemplateId.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/mail/template"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].templateId").value(activeTemplateId))
                .andExpect(jsonPath("$[0].templateName").value("Active name"))
                .andExpect(jsonPath("$[0].templateText").value("Active template"));
    }

    @Test
    @DisplayName("Mail template get API: active template")
    void getMailTemplate_success() throws Exception {
        Long templateId = createTemplate("Creation template", "Template for mail creation");

        mockMvc.perform(get("/api/mail/template")
                .queryParam("templateId", templateId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.templateId").value(templateId))
                .andExpect(jsonPath("$.templateName").value("Creation template"))
                .andExpect(jsonPath("$.templateText").value("Template for mail creation"));
    }

    @Test
    @DisplayName("Mail template get API: inactive template returns 423")
    void getMailTemplate_inactive_returnsLocked() throws Exception {
        Long templateId = createTemplate("Inactive name", "Inactive template");

        mockMvc.perform(delete("/api/mail/template")
                .queryParam("templateId", templateId.toString()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/mail/template")
                .queryParam("templateId", templateId.toString()))
                .andExpect(status().isLocked());
    }

    private Long createTemplate(String templateName, String templateText) throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("templateName", templateName);
        requestBody.put("templateText", templateText);

        String responseBody = mockMvc.perform(post("/api/mail/template")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(responseBody).get("templateId").asLong();
    }
}
