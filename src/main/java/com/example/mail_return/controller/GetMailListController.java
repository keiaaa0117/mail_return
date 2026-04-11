package com.example.mail_return.controller;

import com.example.mail_return.dto.response.GetMailListResponse;
import com.example.mail_return.service.GetMailListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class GetMailListController {

    private final GetMailListService getMailListService;

    public GetMailListController(GetMailListService getMailListService) {
        this.getMailListService = getMailListService;
    }

    @GetMapping
    public List<GetMailListResponse> getMailList() {
        return getMailListService.getMailList();
    }
}
