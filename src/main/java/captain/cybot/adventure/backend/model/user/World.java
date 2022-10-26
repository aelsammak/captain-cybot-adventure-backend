package captain.cybot.adventure.backend.model.user;

import captain.cybot.adventure.backend.model.user.Level;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class World {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String planet;
    private int levelsCompleted;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Level> levels;

    public World(String planet) {
        this.planet = planet;
        levelsCompleted = 0;
        levels = new ArrayList<>();
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void incrementLevelsCompleted() {
        levelsCompleted++;
    }

}
