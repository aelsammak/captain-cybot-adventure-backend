package captain.cybot.adventure.backend.model.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Crossword extends Question {

    @JsonIgnore
    private String[][] crosswordBlock;
    @JsonIgnore
    private String[] hints;

    @JsonIgnore
    private String[] answers;

    public Crossword(String[][] crosswordBlock, String[] hints, String[] answers) {
        super("CROSSWORD");
        this.crosswordBlock = crosswordBlock;
        this.hints = hints;
        this.answers = answers;
    }

    @Override
    public String[] getQuestionAnswers() {
        return  this.answers;
    }
}