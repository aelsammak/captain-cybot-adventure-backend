package captain.cybot.adventure.backend.model.quiz;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Quizzes")
@NoArgsConstructor
@Setter
@Getter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "quiz", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<QuizQuestion> questions;

    private String planet;

    public Quiz(String planet) {
        this.questions = new ArrayList<>();
        this.planet = planet;
    }

    public void addQuestion(QuizQuestion question) {
        this.questions.add(question);
    }
}
