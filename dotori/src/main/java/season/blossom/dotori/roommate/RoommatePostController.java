package season.blossom.dotori.roommate;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import season.blossom.dotori.user.CustomUserDetail;
import season.blossom.dotori.user.User;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoommatePostController {
    private RoommatePostService roommatePostService;


    @GetMapping("/api/board/roommate")
    public ResponseEntity<List<RoommatePostDto>> getPosts() {
        List<RoommatePostDto> roommatePosts = roommatePostService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(roommatePosts);
    }


    @PostMapping("/api/board/roommate/write")
    public ResponseEntity<RoommatePostDto> createPost(@RequestBody RoommatePostDto roommatePostDto,
                                                      @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        User user = customUserDetail.getUser();
        roommatePostDto.setWriter(user);
        RoommatePost roommatePost = roommatePostService.savePost(roommatePostDto);

        RoommatePostDto roommate = RoommatePostDto.builder()
                .id(roommatePost.getId())
                .writer(roommatePost.getWriter())
                .title(roommatePost.getTitle())
                .people(roommatePost.getPeople())
                .dorm_name(roommatePost.getDorm_name())
                .content(roommatePost.getContent())
                .build();

        return ResponseEntity.ok(roommate);
    }


    // 상세 조회
    @GetMapping("/api/board/roommate/{no}")
    public ResponseEntity<RoommatePostDto> getPostDetail(@PathVariable("no") Long no) {
        RoommatePostDto roommatePostDto = roommatePostService.getPost(no);

        return ResponseEntity.status(HttpStatus.OK).body(roommatePostDto);
    }


    @PutMapping("/api/board/roommate/edit/{no}")
    public ResponseEntity<RoommatePostDto> update(@PathVariable("no") Long no, @RequestBody RoommatePostDto roommatePostDto) {
        roommatePostService.updatePost(no, roommatePostDto);

        return ResponseEntity.ok(roommatePostDto);
    }


    @DeleteMapping("/api/board/roommate/delete/{no}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("no") Long no) {
        roommatePostService.deletePost(no);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
