package season.blossom.dotori.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.roommate.RoommatePost;
import season.blossom.dotori.user.User;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryPost deliveryPost;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoommatePost roommatePost;

    private String content;

    @Enumerated(EnumType.STRING)
    private PostType postType;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

}
