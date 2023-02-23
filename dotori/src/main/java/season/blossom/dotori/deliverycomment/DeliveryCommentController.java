package season.blossom.dotori.deliverycomment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import season.blossom.dotori.user.CustomUserDetail;
import season.blossom.dotori.user.User;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;

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
        DeliveryComment deliveryComment = deliveryCommentService.createComment(commentDto);

        DeliveryCommentReturnDto returnDto = DeliveryCommentReturnDto.builder()
                .deliveryCommentId(deliveryComment.getId())
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

    private String filterContent(Long userId, DeliveryComment deliveryComment){
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