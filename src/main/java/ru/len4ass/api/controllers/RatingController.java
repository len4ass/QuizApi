package ru.len4ass.api.controllers;

import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.len4ass.api.models.rating.RatingResponse;
import ru.len4ass.api.services.RatingService;

@RestController
@RequestMapping("/api/rating")
public class RatingController {
    @Resource
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/all")
    public ResponseEntity<RatingResponse> getAllWithPositiveRating() {
        return ratingService.getAllUsersWithPositiveRating();
    }
}
