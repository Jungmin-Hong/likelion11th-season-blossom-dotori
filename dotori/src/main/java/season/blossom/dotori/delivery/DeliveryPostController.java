package season.blossom.dotori.delivery;

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
public class DeliveryPostController {
    private DeliveryPostService deliveryPostService;

    // 목록
//    @GetMapping("/api/board/delivery")
//    public String list(Model model) {
//        List<DeliveryPostDto> deliveryPostList = deliveryPostService.getDeliveryPostList();
//        model.addAttribute("deliveryPostList", deliveryPostList);
//        return "deliveryPost/list";
//    }

    @GetMapping("/api/board/delivery")
    public ResponseEntity<List<DeliveryPostReturnDto>> getPosts() {
        List<DeliveryPostReturnDto> deliveryPosts = deliveryPostService.getList();
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPosts);
    }

//    @GetMapping("/api/board/delivery")
//    public List<DeliveryPostDto> findAllBoard() {
//        return deliveryPostService.getList();
//    }

    @PostMapping("/api/board/delivery/write")
    public ResponseEntity<DeliveryPostReturnDto> createPost(@RequestBody DeliveryPostDto deliveryPostDto,
                                                      @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        User user = customUserDetail.getUser();
        deliveryPostDto.setWriter(user);
        DeliveryPost deliveryPost = deliveryPostService.savePost(deliveryPostDto);

        DeliveryPostReturnDto delivery = DeliveryPostReturnDto.builder()
                .id(deliveryPost.getId())
                .writer(deliveryPost.getWriter().getEmail())
                .title(deliveryPost.getTitle())
                .content(deliveryPost.getContent())
                .createdDate(deliveryPost.getCreatedDate())
                .modifiedDate(deliveryPost.getModifiedDate())
                .build();

        return ResponseEntity.ok(delivery);
    }


    // 상세 조회
    @GetMapping("/api/board/delivery/{no}")
    public ResponseEntity<DeliveryPostReturnDto> getPostDetail(@PathVariable("no") Long no) {
        DeliveryPostReturnDto deliveryPostDto = deliveryPostService.getPost(no);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryPostDto);
    }


    @PutMapping("/api/board/delivery/edit/{no}")
    public ResponseEntity<DeliveryPostReturnDto> update(@PathVariable("no") Long no, @RequestBody DeliveryPostDto deliveryPostDto) {
        deliveryPostService.updatePost(no, deliveryPostDto);

        DeliveryPostReturnDto deliveryPostReturnDto = deliveryPostService.getPost(no);

        return ResponseEntity.ok(deliveryPostReturnDto);
    }


    @DeleteMapping("/api/board/delivery/delete/{no}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("no") Long no) {
        deliveryPostService.deletePost(no);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
