package captain.cybot.adventure.backend.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cosmetics")
@NoArgsConstructor
@Getter
@Setter
public class Cosmetic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private int unlockWorld;

    public Cosmetic(String fileName, int unlockWorld) {
        this.fileName = fileName;
        this.unlockWorld = unlockWorld;
    }

}
