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
public class WordSearch extends Question {
    private String[][] searchArray;

    @JsonIgnore
    private String[] answer;

    public WordSearch(String[][] searchArray, String[] answer) {
        super("WORD_SEARCH");
        this.searchArray = searchArray;
        this.answer = answer;
    }

    @Override
    public String[] getQuestionAnswers() {
        return  this.answer;
    }
}
