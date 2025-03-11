package com.authmicroservice.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProtectedController {

    @GetMapping("/secure-data")
    public String getSecureData() {
        return "Você acessou dados protegidos com JWT!";
    }
}
