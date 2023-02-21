package season.blossom.dotori.delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.delivery.DeliveryPost;

public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {
}
