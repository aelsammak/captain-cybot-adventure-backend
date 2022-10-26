package captain.cybot.adventure.backend.model.question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.io.File;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GuessTheImage extends Question {
    private File filename;
    private String answer;

    public GuessTheImage(File filename, String answer) {
        this.filename = filename;
        this.answer = answer;
    }
}
