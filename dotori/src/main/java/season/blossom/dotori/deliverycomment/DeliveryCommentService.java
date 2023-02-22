package season.blossom.dotori.deliverycomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.delivery.DeliveryPostRepository;

import java.util.List;

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
                .isSecret(commentDto.isSecret())
                .build();

        return deliveryCommentRepository.save(deliveryComment);
    }

    public List<DeliveryComment> getComments(Long postId) {
        List<DeliveryComment> comments = deliveryCommentRepository.findByDeliveryPostIdAndParentCommentIsNull(postId);
//        for (DeliveryComment comment : comments) {
//            if (comment.isSecret()) {
//                comment.setContent("비밀댓글입니다.");
//            }
//            List<DeliveryComment> childComments = deliveryCommentRepository.findByParentCommentId(comment.getId());
//            comment.setChildComment(childComments);
//        }
        return comments;
    }

}
