package ru.len4ass.api.controllers;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.len4ass.api.models.question.QuestionResponse;
import ru.len4ass.api.services.QuestionProviderService;
import ru.len4ass.api.utils.TokenExtractor;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    @Resource
    private final QuestionProviderService questionProviderService;

    public QuestionController(QuestionProviderService questionProviderService) {
        this.questionProviderService = questionProviderService;
    }

    @GetMapping("/get-random-question")
    public ResponseEntity<QuestionResponse> getRandomQuestion(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        var token = TokenExtractor.extractFromHeader(authHeader);
        return questionProviderService.getRandomQuestionIfPossible(token);
    }
}
