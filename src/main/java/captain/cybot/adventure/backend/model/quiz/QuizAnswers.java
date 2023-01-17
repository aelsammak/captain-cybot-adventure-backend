package captain.cybot.adventure.backend.model.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuizAnswers {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private QuizQuestionAnswer[] answers;

    private int score;
}
