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
public class WordScramble extends Question {
    private String scrambledWord;

    @JsonIgnore
    private String answer;

    public WordScramble(String scrambledWord, String answer) {
        super("WORD_SCRAMBLE");
        this.scrambledWord = scrambledWord;
        this.answer = answer;
    }
    @Override
    public String[] getQuestionAnswers() {
        String[] answers = new String[1];
        answers[0] = this.answer;
        return  answers;
    }

}
