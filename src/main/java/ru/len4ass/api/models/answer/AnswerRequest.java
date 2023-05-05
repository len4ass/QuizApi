package ru.len4ass.api.models.answer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerRequest {
    @JsonProperty("answer")
    private String answer;

    public AnswerRequest() {

    }

    public AnswerRequest(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
