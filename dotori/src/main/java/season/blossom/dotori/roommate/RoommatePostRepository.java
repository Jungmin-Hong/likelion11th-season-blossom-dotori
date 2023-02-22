package season.blossom.dotori.roommate;

import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.delivery.DeliveryPost;

public interface RoommatePostRepository extends JpaRepository<RoommatePost, Long> {
}
