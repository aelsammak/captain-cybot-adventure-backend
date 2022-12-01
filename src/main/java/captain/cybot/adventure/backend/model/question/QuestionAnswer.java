package captain.cybot.adventure.backend.model.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionAnswer {

    private String[] answers;

    private boolean isCorrect;
}
