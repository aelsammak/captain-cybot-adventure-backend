package captain.cybot.adventure.backend.model.question;

import captain.cybot.adventure.backend.model.user.Level;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Level level;

    private String type;

    public Question(String type) {
        this.type = type;
    }

}
