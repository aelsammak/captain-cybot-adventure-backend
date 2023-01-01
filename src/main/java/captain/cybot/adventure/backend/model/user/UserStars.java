package captain.cybot.adventure.backend.model.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class UserStars {

    List<Integer> EARTH = new ArrayList<>();
    List<Integer> MARS = new ArrayList<>();
    List<Integer> JUPITER = new ArrayList<>();
    List<Integer> NEPTUNE = new ArrayList<>();
    int totalStars;
}
