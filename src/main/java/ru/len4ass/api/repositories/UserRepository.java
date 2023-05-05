package ru.len4ass.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.len4ass.api.models.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRatingAfter(Double rating);
}
