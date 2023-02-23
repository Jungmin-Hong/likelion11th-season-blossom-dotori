package season.blossom.dotori.roommate;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import season.blossom.dotori.user.University;
import season.blossom.dotori.user.User;

import java.util.List;
import java.util.Optional;

public interface RoommatePostRepository extends JpaRepository<RoommatePost, Long> {

    List<RoommatePost> findAllByWriter_UniversityAndRoommateStatusOrderByCreatedDateDesc(University university, RoommateStatus roommateStatus);
    List<RoommatePost> findAllByWriter_UniversityOrderByCreatedDateDesc(University university);

    @Query("select distinct rp from RoommatePost as rp join rp.comments as rc where rc.writer = :user")
    List<RoommatePost> findAllByCommentWriter(@Param("user") User user);

    @EntityGraph(attributePaths = {"writer"})
    Optional<RoommatePost> findById(Long id);
}
