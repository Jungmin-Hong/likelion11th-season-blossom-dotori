package season.blossom.dotori.roommate;

import lombok.*;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
public class RoommatePostDto {
    private Long id;
    private User writer;
    private String title;
    private Integer people;
    private String dorm_name;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public RoommatePost toEntity() {
        RoommatePost roommatePost = RoommatePost.builder()
//                .id(id)
                .writer(writer)
                .title(title)
                .people(people)
                .dorm_name(dorm_name)
                .content(content)
                .build();
        return roommatePost;
    }

    @Builder
    public RoommatePostDto(Long id, String title, Integer people, String dorm_name, String content, User writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.people = people;
        this.dorm_name = dorm_name;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}