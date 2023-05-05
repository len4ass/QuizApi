package ru.len4ass.api.mappers;

import ru.len4ass.api.models.user.User;
import ru.len4ass.api.models.user.UserWithRating;

public class UserToUserWithRating {
    public static UserWithRating map(User user) {
        return new UserWithRating(user.getUsername(), user.getRating(), user.getCorrectAnswers(), user.getTotalQuestions());
    }
}
