package season.blossom.dotori.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserReturnDto {
    private Integer age;
    private Boolean calling;
    private Boolean smoking;
    private Boolean eating;
    private Integer cleaningCycle;
    private Integer floor;
    private String sleepHabits;
    private String sleepTime;
}
