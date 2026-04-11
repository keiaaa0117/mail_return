package com.example.mail_return;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MailTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("メール作成API: 正常系")
    void createMail_success() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("subject", "テスト件名");
        requestBody.put("message", "テスト本文");

        // レスポンスの検証
        mockMvc.perform(post("/api/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.subject").value("テスト件名"))
                .andExpect(jsonPath("$.message").value("テスト本文"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    @DisplayName("メール論理削除API: 正常系")
    void deleteMail_success() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("subject", "削除対象件名");
        requestBody.put("message", "削除対象本文");

        String responseBody = mockMvc.perform(post("/api/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(responseBody).get("id").asLong();

        mockMvc.perform(delete("/api/mail/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("メール更新API: 正常系")
    void updateMail_success() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("subject", "更新対象件名");
        requestBody.put("message", "更新対象本文");

        String responseBody = mockMvc.perform(post("/api/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(responseBody).get("id").asLong();

        Map<String, String> updateRequestBody = new HashMap<>();
        updateRequestBody.put("subject", "更新後件名");
        updateRequestBody.put("message", "更新後本文");

        mockMvc.perform(put("/api/mail/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequestBody)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.subject").value("更新後件名"))
                .andExpect(jsonPath("$.message").value("更新後本文"));
    }

    @Test
    @DisplayName("メール一覧表示API: 正常系")
    void getMailList_success() throws Exception {
        Map<String, String> firstRequestBody = new HashMap<>();
        firstRequestBody.put("subject", "一覧件名1");
        firstRequestBody.put("message", "一覧本文1");

        Map<String, String> secondRequestBody = new HashMap<>();
        secondRequestBody.put("subject", "一覧件名2");
        secondRequestBody.put("message", "一覧本文2");

        mockMvc.perform(post("/api/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(firstRequestBody)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/mail")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(secondRequestBody)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/api/mail"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].subject").value("一覧件名1"))
                .andExpect(jsonPath("$[0].message").value("一覧本文1"))
                .andExpect(jsonPath("$[1].id").exists())
                .andExpect(jsonPath("$[1].subject").value("一覧件名2"))
                .andExpect(jsonPath("$[1].message").value("一覧本文2"));
    }

}