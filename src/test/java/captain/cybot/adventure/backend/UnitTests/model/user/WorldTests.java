package captain.cybot.adventure.backend.UnitTests.model.user;

import captain.cybot.adventure.backend.model.user.Level;
import captain.cybot.adventure.backend.model.user.User;
import org.junit.jupiter.api.Test;

import captain.cybot.adventure.backend.model.user.World;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTests {

    @Test
    public void setPlanetName(){
        World world = new World("Earth");
        assertEquals("Earth", world.getPlanet());
    }

    @Test
    public void defaultLevelsCompleted() {
        World world = new World("Earth");
        assertEquals(0, world.getLevelsCompleted());
    }

    @Test
    public void incrementLevelsCompleted() {
        World world = new World("Earth");
        assertEquals(0, world.getLevelsCompleted(), "Default levels completed is non-zero");
        world.incrementLevelsCompleted();
        assertEquals(1, world.getLevelsCompleted());
    }

    @Test
    public void addLevel() {
        World world = new World("Earth");
        Level level = new Level(1);
        world.addLevel(level);
        assertEquals(1, world.getLevels().size());
        assertEquals(level, world.getLevels().get(0));
    }
}
