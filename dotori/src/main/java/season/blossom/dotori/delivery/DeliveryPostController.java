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
    public ResponseEntity<List<DeliveryPostDto>> getPosts(@AuthenticationPrincipal CustomUserDetail customUserDetail) {
//        List<DeliveryPost> deliveryPosts = deliveryPostService.getList();
//        List<DeliveryPostDto> deliveryPostDtos = deliveryPosts.stream()
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(deliveryPostDtos);

        List<DeliveryPostDto> deliveryPosts = deliveryPostService.getList();

        return ResponseEntity.status( HttpStatus.OK).body(deliveryPosts);
    }


    // 글 작성
//    @GetMapping("/api/board/delivery/write")
//    public String write() {
//        return "deliveryPost/write";
//    }

    @PostMapping("/api/board/delivery/write")
    public ResponseEntity<DeliveryPostDto> createPost(@RequestBody DeliveryPostDto deliveryPostDto,
                                                      @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        User user = customUserDetail.getUser();
        deliveryPostDto.setWriter(user);
        DeliveryPost deliveryPost = deliveryPostService.savePost(deliveryPostDto);

        DeliveryPostDto delivery = DeliveryPostDto.builder()
                .id(deliveryPost.getId())
                .writer(deliveryPost.getWriter())
                .title(deliveryPost.getTitle())
                .content(deliveryPost.getContent())
                .build();

        return ResponseEntity.ok(delivery);
    }

    // 글 작성 완료 후
//    @PostMapping("/api/board/delivery/write")
//    public String write(DeliveryPostDto deliveryPostDto) {
//        deliveryPostService.savePost(deliveryPostDto);
//        return "redirect:/";
//    }

    // 상세 조회
    @GetMapping("ㅁ")
    public ResponseEntity<DeliveryPostDto> getPostDetail(@PathVariable("no") Long no) {
        DeliveryPostDto deliveryPostDto = deliveryPostService.getPost(no);

        return ResponseEntity.status(HttpStatus.OK).body(deliveryPostDto);
    }

//    // 수정
//    @GetMapping("/api/board/delivery/edit/{no}")
//    public String edit(@PathVariable("no") Long no, Model model) {
//        DeliveryPostDto deliveryPostDto = deliveryPostService.getPost(no);
//
//        model.addAttribute("deliveryPostDto", deliveryPostDto);
//        return "deliveryPost/update";
//    }
//    @PutMapping("/api/board/delivery/edit/{no}")
//    public String update(DeliveryPostDto deliveryPostDto) {
//        deliveryPostService.savePost(deliveryPostDto);
//
//        return "redirect:/";
//    }

    @PutMapping("/api/board/delivery/edit/{no}")
    public ResponseEntity<DeliveryPostDto> update(@PathVariable("no") Long no, @RequestBody DeliveryPostDto deliveryPostDto) {
        DeliveryPostDto deliveryPost = deliveryPostService.getPost(no);
        deliveryPostService.savePost(deliveryPostDto);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPost);
    }



    @DeleteMapping("/api/board/delivery/delete/{no}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("no") Long no) {
        deliveryPostService.deletePost(no);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
