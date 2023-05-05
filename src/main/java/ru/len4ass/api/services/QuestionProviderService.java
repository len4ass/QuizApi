package ru.len4ass.api.services;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.len4ass.api.auth.JwtClaimExtractor;
import ru.len4ass.api.jservice.BadRequestException;
import ru.len4ass.api.jservice.InternalServerErrorException;
import ru.len4ass.api.mappers.QuestionToQuestionResponse;
import ru.len4ass.api.mappers.RandomApiResponseToQuestion;
import ru.len4ass.api.models.question.Question;
import ru.len4ass.api.models.question.QuestionResponse;
import ru.len4ass.api.models.ResponseType;
import ru.len4ass.api.repositories.UserRepository;
import ru.len4ass.api.jservice.JServiceClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuestionProviderService {
    @Resource
    private final UserRepository userRepository;

    @Resource
    private final JwtClaimExtractor jwtClaimExtractor;

    @Resource
    private final JServiceClient jServiceClient;

    public QuestionProviderService(
            UserRepository userRepository,
            JwtClaimExtractor jwtClaimExtractor,
            JServiceClient jServiceClient) {
        this.userRepository = userRepository;
        this.jwtClaimExtractor = jwtClaimExtractor;
        this.jServiceClient = jServiceClient;
    }

    private List<Question> fetchQuestionsFromExternalApi(int questionCount) {
        return Arrays.stream(jServiceClient
                        .getJServiceRandomEndpoint()
                        .getRandomQuestions(questionCount))
                .map(response -> RandomApiResponseToQuestion.map(response))
                .toList();
    }

    private List<Question> fetchQuestionsAndHandleErrors(int questionCount) {
        int retries = 0;
        while (true) {
            try {
                return fetchQuestionsFromExternalApi(questionCount);
            } catch (BadRequestException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed fetching external api due to bad request.");
            } catch (InternalServerErrorException e) {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1000));
                } catch (InterruptedException ignored) {
                }

                retries++;
                if (retries == 5) {
                    throw new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Failed fetching external api due to internal server error on their side");
                }
            }
        }
    }

    public ResponseEntity<QuestionResponse> getRandomQuestionIfPossible(String jwtToken) {
        var username = jwtClaimExtractor.extractUsername(jwtToken);
        var user = userRepository.findByUsername(username).orElseThrow();

        if (user.getQuestion() != null && !user.getHasAnsweredLastQuestion()) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
                    .body(new QuestionResponse(
                            ResponseType.QUESTION_ALREADY_ASSIGNED,
                            user.getQuestion().getQuestion()));
        }

        var question = fetchQuestionsAndHandleErrors(1).get(0);
        user.setQuestion(question);
        user.setHasAnsweredLastQuestion(false);
        user.incrementTotalQuestions();
        userRepository.save(user);
        return ResponseEntity.ok(QuestionToQuestionResponse.map(question));
    }
}
