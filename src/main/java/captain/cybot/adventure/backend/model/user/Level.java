package captain.cybot.adventure.backend.model.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "levels")
@NoArgsConstructor
@Getter
@Setter
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private World world;

    private int levelNumber;
    private int stars;

    public Level(int levelNumber) {
        this.levelNumber = levelNumber;
        stars = 0;
    }

}
