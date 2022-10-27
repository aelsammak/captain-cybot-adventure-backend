package captain.cybot.adventure.backend.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cosmetic_id", referencedColumnName = "id")
    private Cosmetic cosmetic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    @JsonManagedReference
    private List<World> worlds;

    private int totalStars;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
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
