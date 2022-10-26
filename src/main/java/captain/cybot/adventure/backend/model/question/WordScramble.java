package captain.cybot.adventure.backend.model.question;

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
    private String answer;

    public WordScramble(String scrambledWord, String answer) {
        this.scrambledWord = scrambledWord;
        this.answer = answer;
    }
}
