package season.blossom.dotori.roommate;

import lombok.*;
import season.blossom.dotori.delivery.TimeEntity;
import season.blossom.dotori.user.User;

import javax.persistence.*;

@Entity
@Table(name = "roommate_post")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoommatePost extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roommate_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column(nullable = false, length = 30) // 제한 없이도 가능
    private String title;

    // 인실
    @Column(nullable = false)
    private Integer people;

    // 기숙사명
    @Column(nullable = false, length = 30)
    private String dorm_name;

    @Lob
    @Column(nullable = false)
    private String content;


    // 매칭 상태
    @Enumerated(EnumType.STRING)
    private RoommateStatus roommateStatus;


    @Builder
    public RoommatePost(Long id, User writer, Integer people, String dorm_name, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.people = people;
        this.dorm_name = dorm_name;
        this.title = title;
        this.content = content;
        this.roommateStatus = RoommateStatus.MATCHING;
    }
}

