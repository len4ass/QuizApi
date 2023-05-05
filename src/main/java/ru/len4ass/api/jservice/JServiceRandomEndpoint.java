package ru.len4ass.api.jservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.len4ass.api.utils.UriBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class JServiceRandomEndpoint {
    private final String mainUri;
    private final String endpoint = "/api/random";

    private final ObjectMapper objectMapper;

    public JServiceRandomEndpoint(String mainUri) {
        this.mainUri = mainUri;
        objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.findAndRegisterModules();

    }

    private RandomApiResponse[] parseHttpResponse(HttpResponse<String> httpResponse)
            throws JsonProcessingException {
        if (httpResponse.statusCode() >= 400 && httpResponse.statusCode() < 500) {
            throw new BadRequestException();
        }

        if (httpResponse.statusCode() >= 500) {
            throw new InternalServerErrorException();
        }

        return objectMapper.readValue(httpResponse.body(), RandomApiResponse[].class);
    }

    public RandomApiResponse[] getRandomQuestions(int count) {
        if (count < 1) {
            throw new IllegalArgumentException("Count can't be less than 1.");
        }

        if (count > 100) {
            throw new IllegalArgumentException("Count can't be bigger than 100.");
        }

        try {
            var response = HttpClient
                    .newBuilder()
                    .build()
                    .send(HttpRequest.newBuilder(UriBuilder.getUri(
                                            mainUri,
                                            endpoint,
                                            String.format("count=%d", count)))
                    .GET()
                    .build(),
                    HttpResponse.BodyHandlers.ofString());
            return parseHttpResponse(response);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
