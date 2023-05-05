package ru.len4ass.api.services;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.len4ass.api.auth.JwtClaimExtractor;
import ru.len4ass.api.models.answer.AnswerRequest;
import ru.len4ass.api.models.answer.AnswerResponse;
import ru.len4ass.api.models.ResponseType;
import ru.len4ass.api.repositories.UserRepository;

@Service
public class AnswerConsumerService {
    @Resource
    private final UserRepository userRepository;

    @Resource
    private final JwtClaimExtractor jwtClaimExtractor;

    public AnswerConsumerService(
            UserRepository userRepository,
            JwtClaimExtractor jwtClaimExtractor) {
        this.userRepository = userRepository;
        this.jwtClaimExtractor = jwtClaimExtractor;
    }

    public ResponseEntity<AnswerResponse> consumeUserResponse(String jwtToken, AnswerRequest request) {
        var username = jwtClaimExtractor.extractUsername(jwtToken);
        var user = userRepository.findByUsername(username).orElseThrow();

        if (user.getQuestion() == null) {
            return ResponseEntity.badRequest()
                    .body(new AnswerResponse(ResponseType.QUESTION_NOT_ASSIGNED,
                            "You haven't got your question yet, go get it."));
        }

        if (user.getHasAnsweredLastQuestion()) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(new AnswerResponse(ResponseType.ANSWERED_ALREADY,
                            "You have already answered your previous question, get a new one."));
        }

        var question = user.getQuestion();
        if (!question.getAnswer().equals(request.getAnswer())) {
            user.setHasAnsweredLastQuestion(true);
            userRepository.save(user);
            return ResponseEntity.ok(new AnswerResponse(ResponseType.INCORRECT_ANSWER,
                    String.format("Your answer is incorrect. Correct answer was: %s", question.getAnswer())));
        }

        user.setHasAnsweredLastQuestion(true);
        user.updateRating(10D);
        user.incrementCorrectAnswers();
        user = userRepository.save(user);
        return ResponseEntity.ok(new AnswerResponse(ResponseType.SUCCESS,
                String.format("Your answer is correct, gave you 10 points. Your current rating is: %,.2f", user.getRating())));
    }
}
