package ru.len4ass.api.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserWithRating {
    @JsonProperty("username")
    private final String username;

    @JsonProperty("rating")
    private final Double rating;

    @JsonProperty("correct_answers")
    private final Integer correctAnswers;

    @JsonProperty("total_questions")
    private final Integer totalQuestions;

    public UserWithRating(
            String username,
            Double rating,
            Integer correctAnswers,
            Integer totalQuestions) {
        this.username = username;
        this.rating = rating;
        this.correctAnswers = correctAnswers;
        this.totalQuestions = totalQuestions;
    }

    public String getUsername() {
        return username;
    }

    public Double getRating() {
        return rating;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }
}
