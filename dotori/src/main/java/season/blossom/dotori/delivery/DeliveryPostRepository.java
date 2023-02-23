package season.blossom.dotori.delivery;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.user.University;

import java.util.List;
import java.util.Optional;

public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {
    List<DeliveryPost> findAllByWriter_UniversityAndDeliveryStatusOrderByCreatedDateDesc(University university, DeliveryStatus deliveryStatus);
    List<DeliveryPost> findAllByWriter_UniversityOrderByCreatedDateDesc(University university);

    @EntityGraph(attributePaths = {"writer"})
    Optional<DeliveryPost> findById(Long id);
}
