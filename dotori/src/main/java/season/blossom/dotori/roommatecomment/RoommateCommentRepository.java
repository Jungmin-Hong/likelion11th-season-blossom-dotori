package season.blossom.dotori.roommatecomment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoommateCommentRepository extends JpaRepository<RoommateComment, Long> {

    @EntityGraph(attributePaths = {"writer"})
    Optional<RoommateComment> findById(Long id);

    List<RoommateComment> findByRoommatePostIdAndParentCommentIsNull(Long postId);
    List<RoommateComment> findByParentCommentId(Long commentId);
}
