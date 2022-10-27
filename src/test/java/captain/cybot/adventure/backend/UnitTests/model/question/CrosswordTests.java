package captain.cybot.adventure.backend.UnitTests.model.question;

import captain.cybot.adventure.backend.model.question.Crossword;
import captain.cybot.adventure.backend.model.question.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CrosswordTests {

    @Test
    public void isInstanceQuestion() {
        String[][] crosswordBlock = {{"1", "X", "X"}, {"|", "|", "|"}, {"2", "X", "|"}};
        String[] hints = {"Denotes one or more people already mentioned and assumed to be common " +
                             "knowledge", "perform an action"};
        String[] answer = {"the", "do"};
        Crossword crossword = new Crossword(crosswordBlock, hints, answer);

        assertTrue(crossword instanceof Question);
    }

    @Test
    public void setCrosswordBlock() {
        String[][] crosswordBlock = {{"1", "X", "X"}, {"|", "|", "|"}, {"2", "X", "|"}};
        String[] hints = {"Denotes one or more people already mentioned and assumed to be common " +
                "knowledge", "perform an action"};
        String[] answer = {"the", "do"};
        Crossword crossword = new Crossword(crosswordBlock, hints, answer);

        assertEquals(crosswordBlock, crossword.getCrosswordBlock());
    }

    @Test
    public void setHints() {
        String[][] crosswordBlock = {{"1", "X", "X"}, {"|", "|", "|"}, {"2", "X", "|"}};
        String[] hints = {"Denotes one or more people already mentioned and assumed to be common " +
                "knowledge", "perform an action"};
        String[] answer = {"the", "do"};
        Crossword crossword = new Crossword(crosswordBlock, hints, answer);

        assertEquals(hints, crossword.getHints());
    }

    @Test
    public void setAnswer() {
        String[][] crosswordBlock = {{"1", "X", "X"}, {"|", "|", "|"}, {"2", "X", "|"}};
        String[] hints = {"Denotes one or more people already mentioned and assumed to be common " +
                "knowledge", "perform an action"};
        String[] answer = {"the", "do"};
        Crossword crossword = new Crossword(crosswordBlock, hints, answer);

        assertEquals(answer, crossword.getAnswers());
    }
}
