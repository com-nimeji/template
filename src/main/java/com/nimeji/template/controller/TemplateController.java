package com.nimeji.template.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TemplateController {
    @GetMapping
    public String getTemplate() {
        return "template";
    }
}
