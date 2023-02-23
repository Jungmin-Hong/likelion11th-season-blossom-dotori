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
    public ResponseEntity<List<RoommatePostReturnDto>> getPosts() {
        List<RoommatePostReturnDto> roommatePosts = roommatePostService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(roommatePosts);
    }

    @GetMapping("/api/board/roommate/filtered")
    public ResponseEntity<List<RoommatePostReturnDto>> getPostsFiltered() {
        List<RoommatePostReturnDto> roommatePosts = roommatePostService.getListFiltered();
        return ResponseEntity.status(HttpStatus.OK).body(roommatePosts);
    }


    @PostMapping("/api/board/roommate/write")
    public ResponseEntity<RoommatePostReturnDto> createPost(@RequestBody RoommatePostDto roommatePostDto,
                                                      @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        User user = customUserDetail.getUser();
        roommatePostDto.setWriter(user);
        RoommatePost roommatePost = roommatePostService.savePost(roommatePostDto);

        RoommatePostReturnDto roommate = RoommatePostReturnDto.builder()
                .id(roommatePost.getId())
                .writer(roommatePost.getWriter().getEmail())
                .age(roommatePost.getWriter().getAge())
                .calling(roommatePost.getWriter().getCalling())
                .smoking(roommatePost.getWriter().getSmoking())
                .eating(roommatePost.getWriter().getEating())
                .cleaningCycle(roommatePost.getWriter().getCleaningCycle())
                .floor(roommatePost.getWriter().getFloor())
                .sleepHabits(roommatePost.getWriter().getSleepHabits())
                .sleepTime(roommatePost.getWriter().getSleepTime())
                .title(roommatePost.getTitle())
                .people(roommatePost.getPeople())
                .dorm_name(roommatePost.getDorm_name())
                .content(roommatePost.getContent())
                .createdDate(roommatePost.getCreatedDate())
                .modifiedDate(roommatePost.getModifiedDate())
                .build();

        return ResponseEntity.ok(roommate);
    }


    // 상세 조회
    @GetMapping("/api/board/roommate/{no}")
    public ResponseEntity<RoommatePostReturnDto> getPostDetail(@PathVariable("no") Long no) {
        RoommatePostReturnDto roommatePostDto = roommatePostService.getPost(no);

        return ResponseEntity.status(HttpStatus.OK).body(roommatePostDto);
    }


    @PutMapping("/api/board/roommate/edit/{no}")
    public ResponseEntity<RoommatePostReturnDto> update(@PathVariable("no") Long no, @RequestBody RoommatePostDto roommatePostDto) {
        roommatePostService.updatePost(no, roommatePostDto);
        RoommatePostReturnDto roommatePostReturnDto = roommatePostService.getPost(no);
        return ResponseEntity.ok(roommatePostReturnDto);
    }


    @DeleteMapping("/api/board/roommate/delete/{no}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("no") Long no) {
        roommatePostService.deletePost(no);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
