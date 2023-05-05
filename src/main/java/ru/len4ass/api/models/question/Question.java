package ru.len4ass.api.models.question;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Question {
    @Column(length = 1000)
    private String question;

    @Column(length = 1000)
    private String answer;

    public Question() {

    }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Question question1 = (Question) o;
        if (!Objects.equals(question, question1.question)) {
            return false;
        }

        return Objects.equals(answer, question1.answer);
    }

    @Override
    public int hashCode() {
        int result = (question != null ? question.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        return result;
    }
}
