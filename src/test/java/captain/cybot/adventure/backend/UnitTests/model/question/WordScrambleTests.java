package captain.cybot.adventure.backend.UnitTests.model.question;

import captain.cybot.adventure.backend.model.question.Question;
import captain.cybot.adventure.backend.model.question.WordScramble;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordScrambleTests {

    @Test
    public void isInstanceQuestion() {
        WordScramble wordScramble = new WordScramble("almrewa", "malware");

        assertTrue(wordScramble instanceof Question);
    }

    @Test
    public void setFilename() {
        WordScramble wordScramble = new WordScramble("almrewa", "malware");

        assertEquals("almrewa", wordScramble.getScrambledWord());
    }

    @Test
    public void setAnswer() {
        WordScramble wordScramble = new WordScramble("almrewa", "malware");

        assertEquals("malware", wordScramble.getAnswer());
    }
}
