package captain.cybot.adventure.backend.model.question;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class QuestionAnswer {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String[] answers;

    private boolean isCorrect;

    private int stars;

    private int timeTaken; // in s. 120s for 3 stars, 600s 2 stars else 1 star
}
