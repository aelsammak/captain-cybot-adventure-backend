package captain.cybot.adventure.backend.UnitTests.model.question;

import captain.cybot.adventure.backend.model.question.GuessTheImage;
import captain.cybot.adventure.backend.model.question.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuessTheImageTests {

    @Test
    public void isInstanceQuestion() {
        GuessTheImage guessTheImage = new GuessTheImage("image.png", "CorrectAnswer");

        assertTrue(guessTheImage instanceof Question);
    }

    @Test
    public void setFilename() {
        GuessTheImage guessTheImage = new GuessTheImage("image.png", "CorrectAnswer");

        assertEquals("image.png", guessTheImage.getFilename());
    }

    @Test
    public void setAnswer() {
        GuessTheImage guessTheImage = new GuessTheImage("image.png", "CorrectAnswer");

        assertEquals("CorrectAnswer", guessTheImage.getAnswer());
    }
}
