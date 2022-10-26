package captain.cybot.adventure.backend.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
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
