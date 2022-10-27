package captain.cybot.adventure.backend.UnitTests.model.user;

import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.model.user.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    @Test
    public void setUsername() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals("MyUsername", user.getUsername());
    }

    @Test
    public void changeUsername() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals("MyUsername", user.getUsername(), "Constructor username set incorrectly");
        user.setUsername("MyChangedUsername");
        assertEquals("MyChangedUsername", user.getUsername());
    }

    @Test
    public void setEmail() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals("my_email@gmail.com", user.getEmail());
    }

    @Test
    public void changeEmail() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals("my_email@gmail.com", user.getEmail(), "Constructor email set incorrectly");
        user.setEmail("my_changed_email@gmail.com");
        assertEquals("my_changed_email@gmail.com", user.getEmail());
    }

    @Test
    public void setPassword() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void changePasword() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals("password123", user.getPassword(), "Constructor password set incorrectly");
        user.setPassword("changed_password123");
        assertEquals("changed_password123", user.getPassword());
    }

    @Test
    public void defaultStars() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals(0, user.getTotalStars());
    }

    @Test
    public void addStars() {
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        assertEquals(0, user.getTotalStars(), "Default stars is non-zero");
        user.addStars(3);
        assertEquals(3, user.getTotalStars());
    }

    @Test
    public void addWorld() {
        World world = new World("Earth");
        User user = new User("MyUsername", "my_email@gmail.com", "password123");
        user.addWorld(world);
        assertEquals(1, user.getWorlds().size());
        assertEquals(world, user.getWorlds().get(0));
    }

    @Test
    public void defaultCosmetic() {
        fail("Test not implemented");
    }

    @Test
    public void setCosmetic() {
        fail("Test not implemented");
    }
}
