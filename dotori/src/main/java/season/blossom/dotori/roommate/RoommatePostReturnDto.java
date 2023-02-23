package season.blossom.dotori.roommate;

import lombok.Builder;
import lombok.Data;
import season.blossom.dotori.user.User;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
public class RoommatePostReturnDto {
    private Long id;
    private String writer;
    private Integer age;
    private Boolean calling;
    private Boolean smoking;
    private Boolean eating;
    private Integer cleaningCycle;
    private Integer floor;
    private String sleepHabits;
    private String sleepTime;
    private String title;
    private Integer people;
    private String dorm_name;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
