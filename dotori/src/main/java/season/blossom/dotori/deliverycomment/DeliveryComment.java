package season.blossom.dotori.deliverycomment;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "delivery_comments")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "delivery_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DeliveryPost deliveryPost;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User writer;

    private String content;
    private boolean isSecret;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private DeliveryComment parentComment;

    @OneToMany
    private List<DeliveryComment> childComment;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
