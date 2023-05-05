package ru.len4ass.api.controllers;

import jakarta.annotation.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.len4ass.api.models.answer.AnswerRequest;
import ru.len4ass.api.models.answer.AnswerResponse;
import ru.len4ass.api.services.AnswerConsumerService;
import ru.len4ass.api.utils.TokenExtractor;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {
    @Resource
    private final AnswerConsumerService answerConsumerService;

    public AnswerController(AnswerConsumerService answerConsumerService) {
        this.answerConsumerService = answerConsumerService;
    }

    @PostMapping("/answer")
    public ResponseEntity<AnswerResponse> postUserAnswer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody AnswerRequest answerRequest) {
        var token = TokenExtractor.extractFromHeader(authHeader);
        return answerConsumerService.consumeUserResponse(token, answerRequest);
    }
}
