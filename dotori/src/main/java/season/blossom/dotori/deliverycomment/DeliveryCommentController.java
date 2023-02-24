package season.blossom.dotori.deliverycomment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import season.blossom.dotori.user.CustomUserDetail;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/delivery/{postId}/comments")
public class DeliveryCommentController {
    private final DeliveryCommentService deliveryCommentService;

    @PostMapping
    public ResponseEntity<DeliveryCommentReturnDto> createComment(@PathVariable Long postId,
                                                                  @RequestBody DeliveryCommentRequestDto commentDto,
                                                                  @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        commentDto.setWriter(customUserDetail.getUser());
        commentDto.setDeliveryPostId(postId);
        commentDto.setIsSecret(commentDto.getIsSecret() != null && commentDto.getIsSecret());
        DeliveryComment deliveryComment = deliveryCommentService.createComment(commentDto);

        DeliveryCommentReturnDto returnDto = DeliveryCommentReturnDto.builder()
                .commentId(deliveryComment.getId())
                .content(deliveryComment.getContent())
                .writer(deliveryComment.getWriter().getEmail())
                .isSecret(deliveryComment.isSecret())
                .build();

        return ResponseEntity.ok(returnDto);
    }

//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
//                                              @PathVariable Long commentId,
//                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        deliveryCommentService.deleteComment(commentId, userPrincipal.getUser().getId());
//        return ResponseEntity.noContent().build();
//    }
}