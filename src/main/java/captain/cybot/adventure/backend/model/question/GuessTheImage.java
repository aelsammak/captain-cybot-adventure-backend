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
public class GuessTheImage extends Question {
    private String filename;

    @JsonIgnore
    private String answer;

    private int numChars;

    public GuessTheImage(String filename, String answer) {
        super("GUESS_THE_IMAGE");
        this.filename = filename;
        this.answer = answer;
        this.numChars = answer.length();
    }

    @Override
    public String[] getQuestionAnswers() {
        String[] answers = new String[1];
        answers[0] = this.answer;
        return  answers;
    }
}
