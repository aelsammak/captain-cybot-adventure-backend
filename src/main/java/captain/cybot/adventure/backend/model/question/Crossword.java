package captain.cybot.adventure.backend.model.question;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@Getter
@Setter
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
public class Crossword extends Question {

    @Type(type = "string-array")
    @Column(
            name = "crossword_block",
            columnDefinition = "text[][]"
    )
    private String[][] crosswordBlock;

    @Type(type = "string-array")
    @Column(
            name = "hints",
            columnDefinition = "text[]"
    )
    private String[] hints;

    @Type(type = "string-array")
    @Column(
            name = "answers",
            columnDefinition = "text[]"
    )
    private String[] answers;

    public Crossword(String[][] crosswordBlock, String[] hints, String[] answers) {
        super("CROSSWORD");
        this.crosswordBlock = crosswordBlock;
        this.hints = hints;
        this.answers = answers;
    }
}