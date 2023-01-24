package captain.cybot.adventure.backend.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Leaderboard {

    List<LeaderboardEntry> entries;
}
