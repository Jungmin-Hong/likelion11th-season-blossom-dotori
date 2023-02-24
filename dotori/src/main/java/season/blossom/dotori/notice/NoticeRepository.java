package season.blossom.dotori.notice;

import org.springframework.data.jpa.repository.JpaRepository;
import season.blossom.dotori.user.User;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByUser(User user);
    Optional<Notice> findByIdAndUser(Long id, User user);
}
