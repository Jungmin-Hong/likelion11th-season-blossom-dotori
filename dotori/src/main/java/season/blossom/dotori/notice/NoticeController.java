package season.blossom.dotori.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import season.blossom.dotori.user.CustomUserDetail;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeRepository noticeRepository;

    @GetMapping("/api/user/notice")
    public ResponseEntity<List<NoticeReturnDto>> noticeList(@AuthenticationPrincipal CustomUserDetail userDetail){
        List<NoticeReturnDto> returnDtos = noticeRepository.findAllByUser(userDetail.getUser())
                .stream().map(NoticeReturnDto::new).collect(Collectors.toList());

        return ResponseEntity.ok(returnDtos);
    }

    @DeleteMapping("/api/user/notice/{id}")
    public ResponseEntity<HttpStatus> readNotice(@PathVariable("id") Long noticeId, @AuthenticationPrincipal CustomUserDetail userDetail){
        Optional<Notice> notice = noticeRepository.findByIdAndUser(noticeId, userDetail.getUser());
        if(notice.isEmpty())
            throw new IllegalStateException("비허가 요청");

        noticeRepository.delete(notice.get());

        return ResponseEntity.ok(HttpStatus.OK);
    }
}

