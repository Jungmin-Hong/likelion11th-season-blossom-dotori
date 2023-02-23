package season.blossom.dotori.roommatecomment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import season.blossom.dotori.user.CustomUserDetail;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/roommate/{postId}/comments")
public class RoommateCommentController {
    private final RoommateCommentService deliveryCommentService;

    @PostMapping
    public ResponseEntity<RoommateCommentReturnDto> createComment(@PathVariable Long postId,
                                                                  @RequestBody RoommateCommentRequestDto commentDto,
                                                                  @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        commentDto.setWriter(customUserDetail.getUser());
        commentDto.setRoommatePostId(postId);
        RoommateComment deliveryComment = deliveryCommentService.createComment(commentDto);

        RoommateCommentReturnDto returnDto = RoommateCommentReturnDto.builder()
                .commentId(deliveryComment.getId())
                .content(deliveryComment.getContent())
                .writer(deliveryComment.getWriter().getEmail())
                .isSecret(deliveryComment.isSecret())
                .build();

        return ResponseEntity.ok(returnDto);
    }


//    @GetMapping
//    public ResponseEntity<List<DeliveryCommentReturnDto>> getComments(@PathVariable Long postId,
//                                                                      @AuthenticationPrincipal CustomUserDetail customUserDetail) {
//        List<DeliveryComment> comments = deliveryCommentService.getComments(postId, customUserDetail.getUser().getUserId());
//        List<DeliveryCommentReturnDto> returnDtos = comments.stream()
//                .map(comment -> {
//                    List<DeliveryCommentReturnDto> childCommentDtos = comment.getChildComment().stream()
//                            .map(child -> {
//                                String content = filterContent(customUserDetail.getUserId(), child);
//                                DeliveryCommentReturnDto returnDto = DeliveryCommentReturnDto.builder()
//                                        .deliveryCommentId(child.getId())
//                                        .content(content)
//                                        .writer(child.getWriter().getEmail())
//                                        .isSecret(child.isSecret())
//                                        .build();
//                                return returnDto;
//                            })
//                            .collect(Collectors.toList());
//                    return DeliveryCommentReturnDto.builder()
//                            .deliveryCommentId(comment.getId())
//                            .content(filterContent(customUserDetail.getUserId(), comment))
//                            .writer(comment.getWriter().getEmail())
//                            .isSecret(comment.isSecret())
//                            .childCommentList(childCommentDtos.isEmpty() ? null : childCommentDtos)
//                            .build();
//                })
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(returnDtos);
//    }

    private String filterContent(Long userId, RoommateComment deliveryComment){
        if(deliveryComment.isSecret()){
//            if(!(deliveryComment.getDeliveryPost().getWriter().getUserId().equals(userId) ||
//                    deliveryComment.getWriter().getUserId().equals(userId)))
                return "비밀댓글입니다.";
        }
        return deliveryComment.getContent();
    }

//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long postId,
//                                              @PathVariable Long commentId,
//                                              @AuthenticationPrincipal UserPrincipal userPrincipal) {
//        deliveryCommentService.deleteComment(commentId, userPrincipal.getUser().getId());
//        return ResponseEntity.noContent().build();
//    }
}