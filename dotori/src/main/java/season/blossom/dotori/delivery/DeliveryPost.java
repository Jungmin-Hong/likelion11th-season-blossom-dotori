package season.blossom.dotori.delivery;

import lombok.*;
import season.blossom.dotori.user.User;

import javax.persistence.*;

@Entity
@Table(name = "delivery_post")
@Getter @Setter
@ToString
@NoArgsConstructor
public class DeliveryPost extends TimeEntity {

    // 배달 게시글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column(nullable = false, length = 30) // 제한 없이도 가능
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    // 등록 시간
//    private LocalDateTime regTime;

    // 매칭 상태
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;


    @Builder
    public DeliveryPost(Long id, User writer, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.deliveryStatus = DeliveryStatus.MATCHING;
    }

    public void update(String title, String content, User writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
