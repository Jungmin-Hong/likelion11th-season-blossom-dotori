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

    private String store;
    private String place;
    private Integer amount;
    private Integer minimum;

    @Lob
    @Column(nullable = false)
    private String content;

    private Integer numberOfCommentWriter;

    // 매칭 상태
    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    @OneToMany(mappedBy = "deliveryPost")
    private List<DeliveryComment> comments;

    public void increaseNumber(){
        this.numberOfCommentWriter += 1;
    }

    @Builder
    public DeliveryPost(Long id, User writer, String title, String store, String place, Integer amount, Integer minimum, String content, Integer numberOfCommentWriter, MatchingStatus matchingStatus) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.store = store;
        this.place = place;
        this.amount = amount;
        this.minimum = minimum;
        this.content = content;
        this.numberOfCommentWriter = numberOfCommentWriter;
        this.matchingStatus = MatchingStatus.UNMATCHED;
    }
}
