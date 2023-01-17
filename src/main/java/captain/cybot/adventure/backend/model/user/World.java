package captain.cybot.adventure.backend.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worlds")
@NoArgsConstructor
@Getter
@Setter
public class World {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String planet;
    private int levelsCompleted;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "world", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Level> levels;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private User user;

    private int quizScore;

    public World(String planet) {
        this.planet = planet;
        levelsCompleted = 0;
        levels = new ArrayList<>();
        quizScore = -1;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void incrementLevelsCompleted() {
        levelsCompleted++;
    }

}
