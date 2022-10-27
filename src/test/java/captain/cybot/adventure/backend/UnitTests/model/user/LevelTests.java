package captain.cybot.adventure.backend.UnitTests.model.user;

import captain.cybot.adventure.backend.model.user.Level;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevelTests {
    @Test
    public void setPlanetName(){
        Level level = new Level(1);
        assertEquals(1, level.getLevelNumber());
    }

    @Test
    public void defaultStars() {
        Level level = new Level(1);
        assertEquals(0, level.getStars());
    }

    @Test
    public void setStars() {
        Level level = new Level(1);
        assertEquals(0, level.getStars(), "Default stars is non-zero");
        level.setStars(2);
        assertEquals(2, level.getStars());
    }
}
