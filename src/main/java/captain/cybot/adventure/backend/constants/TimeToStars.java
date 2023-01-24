package captain.cybot.adventure.backend.constants;

import lombok.Getter;

@Getter
public enum TimeToStars {

    /* time in s */
    MAX_TIME_3_STARS(120),
    MAX_TIME_2_STARS(600);

    private int time;

    TimeToStars(int time) {this.time = time;}
}
