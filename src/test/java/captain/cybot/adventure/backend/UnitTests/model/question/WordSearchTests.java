package captain.cybot.adventure.backend.UnitTests.model.question;

import captain.cybot.adventure.backend.model.question.Question;
import captain.cybot.adventure.backend.model.question.WordSearch;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordSearchTests {

    @Test
    public void isInstanceQuestion() {
        String[][] searchArray = {{"t", "h", "e"}, {"b", "s", "a"}, {"t", "o", "r"}};
        String[] answer = {"the", "so"};
        WordSearch wordSearch = new WordSearch(searchArray, answer);

        assertTrue(wordSearch instanceof Question);
    }

    @Test
    public void setSearchArray() {
        String[][] searchArray = {{"t", "h", "e"}, {"b", "s", "a"}, {"t", "o", "r"}};
        String[] answer = {"the", "so"};
        WordSearch wordSearch = new WordSearch(searchArray, answer);

        assertEquals(searchArray, wordSearch.getSearchArray());
    }

    @Test
    public void setAnswer() {
        String[][] searchArray = {{"t", "h", "e"}, {"b", "s", "a"}, {"t", "o", "r"}};
        String[] answer = {"the", "so"};
        WordSearch wordSearch = new WordSearch(searchArray, answer);

        assertEquals(answer, wordSearch.getAnswers());
    }
}
