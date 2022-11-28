package captain.cybot.adventure.backend.model.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 6, max = 30, message = "Username must be between 6-30 characters in length")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;


    @NotBlank(message = "Password is required")
    private String password;

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "cosmetic_id", referencedColumnName = "id")
    private Cosmetic cosmetic;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<World> worlds = new ArrayList<>();

    @ManyToMany(fetch =  FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    private int totalStars = 0;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void addWorld(World world) {
        worlds.add(world);
    }

    public void addStars(int stars) {
        totalStars += stars;
    }

}
