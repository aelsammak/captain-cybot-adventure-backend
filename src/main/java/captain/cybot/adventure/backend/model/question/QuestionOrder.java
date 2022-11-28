package captain.cybot.adventure.backend.model.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class QuestionOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private Question question;

    private int questionNumber;
    private String planet;

    public QuestionOrder(Question question,int questionNumber, String planet) {
        this.question = question;
        this.questionNumber = questionNumber;
        this.planet = planet;
    }
}

