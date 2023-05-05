package ru.len4ass.api.jservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomApiResponse {
    @JsonProperty("question")
    private String question;

    @JsonProperty("answer")
    private String answer;

    public RandomApiResponse() {

    }

    public RandomApiResponse(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    @JsonIgnore
    public String getAnswer() {
        return answer;
    }

    @JsonIgnore
    public String getQuestion() {
        return question;
    }
}
