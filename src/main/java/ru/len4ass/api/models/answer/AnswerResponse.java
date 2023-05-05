package ru.len4ass.api.models.answer;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.len4ass.api.models.ResponseType;

public class AnswerResponse {
    @JsonProperty("status")
    private ResponseType answerResponseType;

    @JsonProperty("info")
    private String infoMessage;

    public AnswerResponse() {

    }

    public AnswerResponse(ResponseType answerResponseType, String infoMessage) {
        this.answerResponseType = answerResponseType;
        this.infoMessage = infoMessage;
    }

    public ResponseType getAnswerResponseType() {
        return answerResponseType;
    }

    public String getInfoMessage() {
        return infoMessage;
    }
}
