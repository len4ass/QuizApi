package ru.len4ass.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class HelloController {
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("Hello!");
    }
}
