package captain.cybot.adventure.backend.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LeaderboardEntry {
    private String username;
    private int stars;
    private int position;
}
