package captain.cybot.adventure.backend.UnitTests.model.user;

import captain.cybot.adventure.backend.model.user.Cosmetic;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CosmeticTests {

    @Test
    public void setFilename() {
        Cosmetic cosmetic = new Cosmetic("filename.png", 2);
        assertEquals("filename.png", cosmetic.getFileName());
    }

    @Test
    public void setUnlockWorld() {
        Cosmetic cosmetic = new Cosmetic("filename.png", 2);
        assertEquals(2, cosmetic.getUnlockWorld());
    }
}
