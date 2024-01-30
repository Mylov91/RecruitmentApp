package ru.mylov.Recruitment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recruiter")
public class RecruiterController {
    @GetMapping("/hello-page")
    public ResponseEntity<String> recruiterSayHello() {
        return ResponseEntity.ok("Hello from recruiter page");
    }
}
