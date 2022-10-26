package captain.cybot.adventure.backend.model.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Crossword extends Question {
    private String[][] crosswordBlock;
    private String[] hints;
    private String[] answers;

    public Crossword(String[][] crosswordBlock, String[] hints, String[] answers) {
        this.crosswordBlock = crosswordBlock;
        this.hints = hints;
        this.answers = answers;
    }
}