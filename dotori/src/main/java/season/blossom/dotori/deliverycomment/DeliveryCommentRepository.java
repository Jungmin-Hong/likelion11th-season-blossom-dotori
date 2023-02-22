package season.blossom.dotori.deliverycomment;

import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.delivery.DeliveryPost;

import java.util.List;

public interface DeliveryCommentRepository extends JpaRepository<DeliveryComment, Long> {

    List<DeliveryComment> findByDeliveryPostIdAndParentCommentIsNull(Long postId);
    List<DeliveryComment> findByParentCommentId(Long commentId);
}
