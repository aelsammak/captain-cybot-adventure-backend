package captain.cybot.adventure.backend.model.user;

import captain.cybot.adventure.backend.model.user.User;
import captain.cybot.adventure.backend.model.user.World;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class WorldProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;
    private int totalStars;

    @OneToMany(fetch = FetchType.LAZY)
    private List<World> worlds;

    public WorldProgress(User user) {
        this.user = user;
        totalStars = 0;
        worlds = new ArrayList<>();
    }

    public void addWorld(World world) {
        worlds.add(world);
    }

    public void addStars(int stars) {
        totalStars += stars;
    }

}
