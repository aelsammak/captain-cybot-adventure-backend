package captain.cybot.adventure.backend.model.question;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.hibernate.internal.util.collections.ArrayHelper.toList;

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
    @JsonIgnore
    private String[][] crosswordBlock;

    @Type(type = "string-array")
    @Column(
            name = "hints",
            columnDefinition = "text[]"
    )
    @JsonIgnore
    private String[] hints;

    @Type(type = "string-array")
    @Column(
            name = "answers",
            columnDefinition = "text[]"
    )
    @JsonIgnore
    private String[] answers;

    public Crossword(String[][] crosswordBlock, String[] hints, String[] answers) {
        super("CROSSWORD");
        this.crosswordBlock = crosswordBlock;
        this.hints = hints;
        this.answers = answers;
    }

    private static Map<String, Object> toMap(JSONObject jsonobj) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            Iterator<String> keys = jsonobj.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonobj.get(key);
                if (value instanceof JSONArray) {
                    value = toList((JSONArray) value);
                } else if (value instanceof JSONObject) {
                    value = toMap((JSONObject) value);
                }
                map.put(key, value);
            }
            return map;
        } catch(Exception e) {return null;}
    }

    private String getDirection(int cellCol, int cellRow, String[][] searchBlock) {
        boolean isMaxCol = (cellCol == searchBlock[0].length-1);
        boolean isMaxRow= (cellRow == searchBlock.length-1);

        String dir;

        if (!isMaxCol && !isMaxRow) {
            if (searchBlock[cellRow][cellCol+1].equals("|")) {
                dir = "down";
            } else {
                dir = "across";
            }
        } else if (isMaxCol && !isMaxRow) {
            dir = "down";
        } else if (!isMaxCol) {
            dir = "across";
        } else {
            if (searchBlock[0][1].equals("|")) {
                dir = "down";
            } else {
                dir = "across";
            }
        }

        return dir;
    }

    public Map<String, Object> getResData() {
        String[][] searchBlock = this.crosswordBlock;
        String[] hints = this.hints;
        String[] answers = this.answers;
        JSONObject jsonObject = new JSONObject();
        JSONObject acrossJson = new JSONObject();
        JSONObject downJson = new JSONObject();

        for (int i = 0; i < searchBlock.length; i++) {
            for (int j = 0; j < searchBlock[i].length; j++) {
                try {
                    String cell = searchBlock[i][j];
                    int cellValue = Integer.parseInt(cell);
                    String dir = getDirection(j, i, searchBlock);
                    JSONObject tmpObj = new JSONObject();
                    tmpObj.put("clue", hints[cellValue-1]);
                    String answerStr = answers[cellValue-1];
                    tmpObj.put("answer", answerStr.toUpperCase());
                    tmpObj.put("row", i);
                    tmpObj.put("col", j);
                    if (dir.equals("across")) {
                        acrossJson.put(cell, tmpObj);
                    } else {
                        downJson.put(cell, tmpObj);
                    }
                } catch (Exception e) {}
            }
        }
        try {
            jsonObject.put("type", "CROSSWORD");
            jsonObject.put("across", acrossJson);
            jsonObject.put("down", downJson);
        } catch(Exception e) {}
        return toMap(jsonObject);
    }

    @Override
    public String toString() {
        return getResData().toString();
    }

    @Override
    public String[] getQuestionAnswers() {
        return  this.answers;
    }
}