package season.blossom.dotori.delivery;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import season.blossom.dotori.user.University;
import season.blossom.dotori.user.User;

import java.util.List;
import java.util.Optional;

public interface DeliveryPostRepository extends JpaRepository<DeliveryPost, Long> {
    List<DeliveryPost> findAllByWriter_UniversityAndDeliveryStatusOrderByCreatedDateDesc(University university, DeliveryStatus deliveryStatus);
    List<DeliveryPost> findAllByWriter_UniversityOrderByCreatedDateDesc(University university);

    @Query("select distinct dp from DeliveryPost as dp join dp.comments as dc where dc.writer = :user")
    List<DeliveryPost> findAllByCommentWriter(@Param("user") User user);

    @EntityGraph(attributePaths = {"writer"})
    Optional<DeliveryPost> findById(Long id);
}
