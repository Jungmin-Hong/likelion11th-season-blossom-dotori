package season.blossom.dotori.deliverycomment;

import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.user.User;

import java.util.Optional;

public interface DeliveryCommentSeqRepository extends JpaRepository<DeliveryCommentSeq, Long> {
    Optional<DeliveryCommentSeq> findByUserAndDeliveryPost(User user, DeliveryPost deliveryPost);
}
