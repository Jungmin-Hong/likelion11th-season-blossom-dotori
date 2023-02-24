package season.blossom.dotori.roommatecomment;

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
@Getter
@Builder
public class RoommateCommentSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roommate_comment_seq_id")
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private RoommatePost roommatePost;

    private int writeSeq;
}
