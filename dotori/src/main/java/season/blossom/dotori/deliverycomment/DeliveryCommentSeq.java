package season.blossom.dotori.deliverycomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.user.User;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class DeliveryCommentSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dorm_comment_seq_id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private DeliveryPost deliveryPost;

    private int writeSeq;
}
