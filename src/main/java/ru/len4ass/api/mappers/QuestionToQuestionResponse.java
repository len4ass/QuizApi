package ru.len4ass.api.mappers;

import ru.len4ass.api.models.question.Question;
import ru.len4ass.api.models.question.QuestionResponse;
import ru.len4ass.api.models.ResponseType;

public class QuestionToQuestionResponse {
    public static QuestionResponse map(Question question) {
        return new QuestionResponse(ResponseType.SUCCESS, question.getQuestion());
    }
}
