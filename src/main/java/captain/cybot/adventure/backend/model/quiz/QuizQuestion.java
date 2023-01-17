package captain.cybot.adventure.backend.model.quiz;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "QuizQuestions")
@NoArgsConstructor
@Getter
@Setter
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Quiz quiz;

    private String question;

    @Type(type = "string-array")
    @Column(
            name = "options",
            columnDefinition = "text[]"
    )
    private String[] options;

    private int questionNumber;

    @JsonIgnore
    private String answer;

    public QuizQuestion(String question, String[] options, String answer, int questionNumber) {
        this.question = question;
        this.options = options;
        this.answer = answer;
        this.questionNumber = questionNumber;
    }
}
