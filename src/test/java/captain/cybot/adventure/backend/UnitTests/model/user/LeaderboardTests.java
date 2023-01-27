package captain.cybot.adventure.backend.UnitTests.model.user;

import captain.cybot.adventure.backend.model.user.Leaderboard;
import captain.cybot.adventure.backend.model.user.LeaderboardEntry;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LeaderboardTests {

    @Test
    public void entryCreationSetUsername() {
        LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
        leaderboardEntry.setUsername("MyUsername");
        assertEquals(leaderboardEntry.getUsername(), "MyUsername");
    }

    @Test
    public void entryCreationSetStars() {
        LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
        leaderboardEntry.setStars(15);
        assertEquals(leaderboardEntry.getStars(), 15);
    }

    @Test
    public void entryCreationSetPosition() {
        LeaderboardEntry leaderboardEntry = new LeaderboardEntry();
        leaderboardEntry.setPosition(12);
        assertEquals(leaderboardEntry.getPosition(), 12);
    }

    @Test
    public void addEntries() {
        LeaderboardEntry leaderboardEntry1 = new LeaderboardEntry();
        leaderboardEntry1.setUsername("MyUsername");
        leaderboardEntry1.setStars(15);
        leaderboardEntry1.setPosition(12);

        LeaderboardEntry leaderboardEntry2 = new LeaderboardEntry();
        leaderboardEntry2.setUsername("MyUsername1");
        leaderboardEntry2.setStars(20);
        leaderboardEntry2.setPosition(9);


        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setEntries(new ArrayList<>());
        leaderboard.getEntries().add(leaderboardEntry1);
        leaderboard.getEntries().add(leaderboardEntry2);

        List<LeaderboardEntry> leaderboardEntries = new ArrayList<>();
        leaderboardEntries.add(leaderboardEntry1);
        leaderboardEntries.add(leaderboardEntry2);

        assertEquals(leaderboard.getEntries(), leaderboardEntries);
    }
}
