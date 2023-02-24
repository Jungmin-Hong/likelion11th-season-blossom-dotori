package season.blossom.dotori.deliverycomment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliveryCommentRepository extends JpaRepository<DeliveryComment, Long> {

    @EntityGraph(attributePaths = {"writer"})
    Optional<DeliveryComment> findById(Long id);

    List<DeliveryComment> findByDeliveryPostIdAndParentCommentIsNull(Long postId);
}
