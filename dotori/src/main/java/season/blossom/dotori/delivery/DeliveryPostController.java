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


    @GetMapping("/api/board/delivery")
    public ResponseEntity<List<DeliveryPostReturnDto>> getPosts(@RequestParam(name = "matchType", required = false, defaultValue = "0") int matchType,
                                                                    @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        //매치타입 1 = 매칭되지 않은 게시글만 필터, 매치타입 0 혹은 그 외 숫자 = 모든 게시글 불러오기
        List<DeliveryPostReturnDto> deliveryPosts = deliveryPostService.getList(customUserDetail.getUser(), matchType);
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPosts);
    }


    @PostMapping("/api/board/delivery/write")
    public ResponseEntity<DeliveryPostReturnDto> createPost(@RequestBody DeliveryPostDto deliveryPostDto,
                                                      @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        User user = customUserDetail.getUser();
        deliveryPostDto.setWriter(user);
        DeliveryPostReturnDto deliveryPostReturn = deliveryPostService.savePost(deliveryPostDto);


        return ResponseEntity.ok(deliveryPostReturn);
    }


    // 상세 조회
    @GetMapping("/api/board/delivery/{no}")
    public ResponseEntity<DeliveryPostReturnDto> getPostDetail(@PathVariable("no") Long no, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        DeliveryPostReturnDto deliveryPostDto = deliveryPostService.getPost(no, customUserDetail.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPostDto);
    }

    @PostMapping("/api/board/delivery/{no}/match")
    public ResponseEntity<DeliveryPostReturnDto> postMatchStatus(@PathVariable("no") Long no, @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        DeliveryPostReturnDto deliveryPostDto = deliveryPostService.postMatchStatus(no, customUserDetail.getUserId());
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

    // 목록
//    @GetMapping("/api/board/delivery")
//    public String list(Model model) {
//        List<DeliveryPostDto> deliveryPostList = deliveryPostService.getDeliveryPostList();
//        model.addAttribute("deliveryPostList", deliveryPostList);
//        return "deliveryPost/list";
//    }

    @GetMapping("/api/board/delivery/filtered")
    public ResponseEntity<List<DeliveryPostReturnDto>> getPostsFiltered() {
        List<DeliveryPostReturnDto> deliveryPosts = deliveryPostService.getListFiltered();
        return ResponseEntity.status(HttpStatus.OK).body(deliveryPosts);
    }

}
