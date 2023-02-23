package season.blossom.dotori.roommatecomment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "roommate_comments")
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RoommateComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roommate_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryPost roommatePost;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    private String content;
    private boolean isSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoommateComment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<RoommateComment> childComment;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
