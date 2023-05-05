package ru.len4ass.api.mappers;

import ru.len4ass.api.jservice.RandomApiResponse;
import ru.len4ass.api.models.question.Question;

public class RandomApiResponseToQuestion {
    public static Question map(RandomApiResponse response) {
        return new Question(response.getQuestion(), response.getAnswer());
    }
}
