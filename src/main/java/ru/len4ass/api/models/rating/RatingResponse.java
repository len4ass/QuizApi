package ru.len4ass.api.models.rating;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.len4ass.api.models.user.UserWithRating;

import java.util.List;

public class RatingResponse {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("users")
    private List<UserWithRating> users;

    public RatingResponse(Integer count, List<UserWithRating> users) {
        this.count = count;
        this.users = users;
    }

    public Integer getCount() {
        return count;
    }

    public List<UserWithRating> getUsers() {
        return users;
    }
}
