package season.blossom.dotori.roommate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.roommatecomment.RoommateComment;
import season.blossom.dotori.roommatecomment.RoommateCommentReturnDto;
import season.blossom.dotori.user.User;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RoommatePostReturnDto {
    private Long id;
    private String writer;
    private String title;
    private Boolean gender;
    private Integer age;
    private String dorm;
    private Integer floor;
    private Integer people;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private RoommateStatus roommateStatus;
    private List<RoommateCommentReturnDto> comments;

    public RoommatePostReturnDto(RoommatePost roommatePost) {
        this.id = roommatePost.getId();
        this.writer = roommatePost.getWriter().getName();
        this.title = roommatePost.getTitle();
        this.gender = roommatePost.getWriter().getGender();
        this.age = roommatePost.getWriter().getAge();
        this.dorm = roommatePost.getWriter().getDorm();
        this.floor = roommatePost.getWriter().getFloor();
        this.people = roommatePost.getPeople();
        this.content = roommatePost.getContent();
        this.createdDate = roommatePost.getCreatedDate();
        this.modifiedDate = roommatePost.getModifiedDate();
        this.roommateStatus = roommatePost.getRoommateStatus();
    }
}
