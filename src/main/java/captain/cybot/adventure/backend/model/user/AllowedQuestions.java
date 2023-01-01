package captain.cybot.adventure.backend.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AllowedQuestions {

    private String planet;
    private int questionNumber;
}
