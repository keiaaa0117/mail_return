package com.example.mail_return.controller;

import com.example.mail_return.service.DeleteMailService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
public class DeleteMailController {

    private final DeleteMailService deleteMailService;

    public DeleteMailController(DeleteMailService deleteMailService) {
        this.deleteMailService = deleteMailService;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logicalDelete(@PathVariable Long id) {
        deleteMailService.logicalDelete(id);
    }
}