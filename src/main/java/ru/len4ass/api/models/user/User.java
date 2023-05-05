package ru.len4ass.api.models.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.len4ass.api.models.question.Question;

import java.util.*;

@Entity
@Table(name = "user_data")
public class User implements UserDetails {
    @Id
    @GeneratedValue()
    private Integer id;
    private String username;
    private String password;
    @Embedded
    private Question lastQuestion;
    private Integer totalQuestions;
    private Integer correctAnswers;
    private Double rating;
    private Boolean hasAnsweredLastQuestion;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public User() {

    }

    public User(String username, String password) {
        this(0, username, password, UserRole.Normal);
    }

    public User(Integer id, String username, String password, UserRole userRole) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.lastQuestion = null;
        this.hasAnsweredLastQuestion = false;
        this.rating = 0D;
        this.totalQuestions = 0;
        this.correctAnswers = 0;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Question getQuestion() {
        return lastQuestion;
    }

    public void setQuestion(Question question) {
        this.lastQuestion = question;
    }

    public Boolean getHasAnsweredLastQuestion() {
        return hasAnsweredLastQuestion;
    }

    public void setHasAnsweredLastQuestion(Boolean hasAnsweredLastQuestion) {
        this.hasAnsweredLastQuestion = hasAnsweredLastQuestion;
    }

    public Integer getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(Integer totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void incrementTotalQuestions() {
        this.totalQuestions++;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void incrementCorrectAnswers() {
        this.correctAnswers++;
    }

    public Double getRating() {
        return rating;
    }

    public void updateRating(Double rating) {
        this.rating += rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        if (!Objects.equals(id, user.id)) {
            return false;
        }

        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }
}
