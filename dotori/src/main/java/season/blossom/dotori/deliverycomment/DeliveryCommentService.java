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

        DeliveryPost deliveryPost = deliveryPostRepository.findById(commentDto.getDeliveryPostId()).orElse(null);
        DeliveryComment parentComment = null;
        boolean isSecret;

        if(commentDto.getParentCommentId() != null) {
            parentComment = deliveryCommentRepository.findById(commentDto.getParentCommentId()).orElse(null);
        }

        //원
        if(parentComment == null)
            isSecret = commentDto.getIsSecret();
        else
            isSecret = parentComment.isSecret();

        if(parentComment.getParentComment() != null){
            parentComment = parentComment.getParentComment();
        }

        if(isSecret){
            boolean isForbidden = false;
            Long curRequestUserId = commentDto.getWriter().getUserId();
            Long postWriterId = deliveryPost.getWriter().getUserId();
            //작성자가 답글이 아닌 비밀 댓글을 다는 것은 불가능
            if(parentComment==null && postWriterId == curRequestUserId)
                isForbidden = true;


            //원 댓글이 비밀댓글이 아닌데 비밀답글을 다는 것은 불가능
            if(parentComment!=null && !parentComment.isSecret())
                isForbidden = true;

            //비밀댓글에 답글을 작성할 수 있는 사용자는 글 작성자와 원 댓글 작성자만 허용, 그 외에는 금지
            if(parentComment!=null &&
                    curRequestUserId != parentComment.getWriter().getUserId() &&
                    curRequestUserId != postWriterId)
                isForbidden = true;

            if(isForbidden)
                throw new IllegalStateException();
        }

        DeliveryComment deliveryComment = DeliveryComment.builder()
                .deliveryPost(deliveryPost)
                .parentComment(parentComment)
                .writer(commentDto.getWriter())
                .content(commentDto.getContent())
                .isSecret(isSecret)
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
                                        .commentId(child.getId())
                                        .content(content)
                                        .writer(child.getWriter().getEmail())
                                        .isSecret(child.isSecret())
                                        .build();
                                return returnDto;
                            })
                            .collect(Collectors.toList());
                    return DeliveryCommentReturnDto.builder()
                            .commentId(comment.getId())
                            .content(filterContent(userId, comment))
                            .writer(comment.getWriter().getEmail())
                            .isSecret(comment.isSecret())
                            .childCommentList(childCommentDtos.isEmpty() ? null : childCommentDtos)
                            .build();
                })
                .collect(Collectors.toList());
        return returnDtos;
    }

    public void addMatchingUser(Long userId, Long postId, Long commentId) {
        DeliveryComment deliveryComment = deliveryCommentRepository.findById(commentId).orElse(null);
        DeliveryPost deliveryPost = deliveryPostRepository.findById(postId).orElse(null);

        if(deliveryPost.getWriter().getUserId() == userId){
            deliveryPost.getMatchedUsers().add(deliveryComment.getWriter());
        }
        else{
            throw new RuntimeException();
        }
    }
        private String filterContent(Long userId, DeliveryComment deliveryComment){
        if(deliveryComment.isSecret()){
            if(!(deliveryComment.getDeliveryPost().getWriter().getUserId() == userId ||
                    deliveryComment.getWriter().getUserId() == userId))
            return "비밀댓글입니다.";
        }
        return deliveryComment.getContent();
    }
}
