package season.blossom.dotori.deliverycomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.delivery.DeliveryPostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCommentService {
    private final DeliveryPostRepository deliveryPostRepository;
    private final DeliveryCommentRepository deliveryCommentRepository;

    public DeliveryComment createComment(DeliveryCommentRequestDto commentDto){

        DeliveryComment parentComment = null;

        if(commentDto.getParentCommentId() != null) {
            parentComment = deliveryCommentRepository.findById(commentDto.getParentCommentId()).orElse(null);
        }

        DeliveryPost deliveryPost = deliveryPostRepository.findById(commentDto.getDeliveryPostId()).orElse(null);
        DeliveryComment deliveryComment = DeliveryComment.builder()
                .deliveryPost(deliveryPost)
                .parentComment(parentComment)
                .writer(commentDto.getWriter())
                .content(commentDto.getContent())
                .isSecret(commentDto.getIsSecret())
                .build();

        return deliveryCommentRepository.save(deliveryComment);
    }

    public List<DeliveryCommentReturnDto> getComments(Long postId, Long userId) {
        List<DeliveryComment> comments = deliveryCommentRepository.findByDeliveryPostIdAndParentCommentIsNull(postId);

        List<DeliveryCommentReturnDto> returnDtos = comments.stream()
                .map(comment -> {
                    List<DeliveryCommentReturnDto> childCommentDtos = comment.getChildComment().stream()
                            .map(child -> {
                                String content = filterContent(userId, child);
                                DeliveryCommentReturnDto returnDto = DeliveryCommentReturnDto.builder()
                                        .deliveryCommentId(child.getId())
                                        .content(content)
                                        .writer(child.getWriter().getEmail())
                                        .isSecret(child.isSecret())
                                        .build();
                                return returnDto;
                            })
                            .collect(Collectors.toList());
                    return DeliveryCommentReturnDto.builder()
                            .deliveryCommentId(comment.getId())
                            .content(filterContent(userId, comment))
                            .writer(comment.getWriter().getEmail())
                            .isSecret(comment.isSecret())
                            .childCommentList(childCommentDtos.isEmpty() ? null : childCommentDtos)
                            .build();
                })
                .collect(Collectors.toList());
        return returnDtos;
    }

    private String filterContent(Long userId, DeliveryComment deliveryComment){
        if(deliveryComment.isSecret()){
            if(!(deliveryComment.getDeliveryPost().getWriter().getUserId().equals(userId) ||
                    deliveryComment.getWriter().getUserId().equals(userId)))
            return "비밀댓글입니다.";
        }
        return deliveryComment.getContent();
    }
}
