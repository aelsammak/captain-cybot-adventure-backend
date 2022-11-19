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
public class WordSearch extends Question {

    @Type(type = "string-array")
    @Column(
            name = "search_array",
            columnDefinition = "text[][]"
    )
    private String[][] searchArray;

    @Type(type = "string-array")
    @Column(
            name = "answers",
            columnDefinition = "text[]"
    )
    private String[] answers;

    public WordSearch(String[][] searchArray, String[] answers) {
        super("WORD_SEARCH");
        this.searchArray = searchArray;
        this.answers = answers;
    }
}
