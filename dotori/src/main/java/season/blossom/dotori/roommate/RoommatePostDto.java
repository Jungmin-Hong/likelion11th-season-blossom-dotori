package season.blossom.dotori.roommate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
public class RoommatePostDto {
    private Long id;
    @JsonIgnore
    private User writer;
    private String title;
    private Integer people;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private RoommateStatus roommateStatus;

    public RoommatePost toEntity() {
        RoommatePost roommatePost = RoommatePost.builder()
//                .id(id)
                .writer(writer)
                .title(title)
                .people(people)
                .content(content)
                .build();
        return roommatePost;
    }

    @Builder
    public RoommatePostDto(Long id, String title, Integer people, String content, User writer, LocalDateTime createdDate, LocalDateTime modifiedDate, RoommateStatus roommateStatus) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.people = people;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.roommateStatus = roommateStatus;
    }
}