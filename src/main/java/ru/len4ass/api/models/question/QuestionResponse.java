package ru.len4ass.api.models.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import ru.len4ass.api.models.ResponseType;

public class QuestionResponse {
    @JsonProperty("status")
    private ResponseType responseType;
    @JsonProperty("question")
    private String question;

    public QuestionResponse() {

    }

    public QuestionResponse(ResponseType responseType,
                            String question) {
        this.responseType = responseType;
        this.question = question;
    }
}
