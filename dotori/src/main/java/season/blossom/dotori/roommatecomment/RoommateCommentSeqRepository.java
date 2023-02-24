package season.blossom.dotori.roommatecomment;

import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.roommate.RoommatePost;
import season.blossom.dotori.user.User;

import java.util.Optional;

public interface RoommateCommentSeqRepository extends JpaRepository<RoommateCommentSeq, Long> {
    Optional<RoommateCommentSeq> findByUserAndRoommatePost(User user, RoommatePost roommatePost);
}
