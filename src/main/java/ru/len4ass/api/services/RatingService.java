package ru.len4ass.api.services;

import jakarta.annotation.Resource;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.len4ass.api.mappers.UserToUserWithRating;
import ru.len4ass.api.models.rating.RatingResponse;
import ru.len4ass.api.repositories.UserRepository;

@Service
public class RatingService {
    @Resource
    private final UserRepository userRepository;

    public RatingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<RatingResponse> getAllUsersWithPositiveRating() {
        var users = userRepository.findAllByRatingAfter(0D)
                .stream()
                .map(user -> UserToUserWithRating.map(user))
                .toList();

        return ResponseEntity.ok(new RatingResponse(users.size(), users));
    }
}
