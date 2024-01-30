package ru.mylov.Recruitment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @GetMapping("/hello-page")
    public ResponseEntity<String> customerSayHello() {
        return ResponseEntity.ok("Hello from customer page");
    }
}
