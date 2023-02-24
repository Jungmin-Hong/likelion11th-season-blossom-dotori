package season.blossom.dotori.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import season.blossom.dotori.roommate.RoommatePost;

@Data
@Builder
@AllArgsConstructor
public class UserReturnDto {
    private Long userId;
    // 성별
    private Boolean gender;
    // 나이
    private Integer age;
    // 기숙사명
    private String dorm;
    // 선호 층수
    private Integer floor;
    // 청소 주기
    private String cleaningCycle;
    // 소등 시간
    private String sleepTime;
    // 잠버릇
    private String sleepHabits;
    // 기숙사 이용시간
    private String useTime;
    // 본인 흡연
    private Boolean smoking;
    // 식사 가능 여부
    private Boolean eating;
    // 통화 가능 여부
    private Boolean calling;
    // 룸메 흡연 여부
    private Boolean smokeMate;

    public UserReturnDto(User user) {
        this.userId = user.getUserId();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.dorm = user.getDorm();
        this.floor = user.getFloor();
        this.cleaningCycle = user.getCleaningCycle();
        this.sleepTime = user.getSleepTime();
        this.sleepHabits = user.getSleepHabits();
        this.useTime = user.getUseTime();
        this.smoking = user.getSmoking();
        this.eating = user.getEating();
        this.calling = user.getCalling();
        this.smokeMate = user.getSmokeMate();
    }
}
