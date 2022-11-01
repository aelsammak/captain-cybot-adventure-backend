package captain.cybot.adventure.backend.model.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class WordSearch extends Question {
    private String[][] searchArray;
    private String[] answer;

    public WordSearch(String[][] searchArray, String[] answer) {
        super("WORD_SEARCH");
        this.searchArray = searchArray;
        this.answer = answer;
    }
}
