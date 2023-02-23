package season.blossom.dotori.delivery;

import lombok.*;
import season.blossom.dotori.deliverycomment.DeliveryComment;
import season.blossom.dotori.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "delivery_post")
@Getter @Setter
@ToString
@NoArgsConstructor
public class DeliveryPost extends TimeEntity {

    // 배달 게시글 번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(mappedBy = "deliveryPost")
    private List<DeliveryComment> comments;

    @ManyToMany
    @JoinTable(
            name = "delivery_post_matching",
            joinColumns = @JoinColumn(name = "delivery_post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> matchedUsers;


    @Builder
    public DeliveryPost(Long id, User writer, String title, String content, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.deliveryStatus = deliveryStatus;
    }
}
