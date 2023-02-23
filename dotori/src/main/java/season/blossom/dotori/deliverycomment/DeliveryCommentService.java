package season.blossom.dotori.deliverycomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.delivery.DeliveryPostRepository;
import season.blossom.dotori.user.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryCommentService {
    private final DeliveryCommentSeqRepository deliveryCommentSeqRepository;
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

        if(parentComment != null && parentComment.getParentComment() != null){
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

        DeliveryCommentSeq foundSeq = deliveryCommentSeqRepository.findByUserAndDeliveryPost(commentDto.getWriter(), deliveryPost).orElse(null);
        if(deliveryPost.getWriter().getUserId() != commentDto.getWriter().getUserId()) {
            if(foundSeq == null){
                deliveryPost.increaseNumber();
                DeliveryCommentSeq curSeq = DeliveryCommentSeq.builder()
                        .writeSeq(deliveryPost.getNumberOfCommentWriter())
                        .user(commentDto.getWriter())
                        .deliveryPost(deliveryPost)
                        .build();
                deliveryCommentSeqRepository.save(curSeq);
            }
        }

        deliveryPost.increaseNumber();

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
        DeliveryPost deliveryPost = deliveryPostRepository.findById(postId).orElse(null);

        List<DeliveryCommentReturnDto> returnDtos = comments.stream()
                .map(comment -> {
                    List<DeliveryCommentReturnDto> childCommentDtos = comment.getChildComment().stream()
                            .map(child -> {
                                String content = filterContent(userId, child);
                                String writerAlias = convertWriter(child.getWriter(), deliveryPost);
                                DeliveryCommentReturnDto returnDto = DeliveryCommentReturnDto.builder()
                                        .commentId(child.getId())
                                        .content(content)
                                        .writer(writerAlias)
                                        .isSecret(child.isSecret())
                                        .build();
                                return returnDto;
                            })
                            .collect(Collectors.toList());
                    return DeliveryCommentReturnDto.builder()
                            .commentId(comment.getId())
                            .content(filterContent(userId, comment))
                            .writer(convertWriter(comment.getWriter(), deliveryPost))
                            .isSecret(comment.isSecret())
                            .childCommentList(childCommentDtos.isEmpty() ? null : childCommentDtos)
                            .build();
                })
                .collect(Collectors.toList());
        return returnDtos;
    }


    private String filterContent(Long userId, DeliveryComment deliveryComment){
        if(deliveryComment.isSecret()){
            if(!(deliveryComment.getDeliveryPost().getWriter().getUserId() == userId ||
                    deliveryComment.getWriter().getUserId() == userId))
            return "비밀댓글입니다.";
        }
        return deliveryComment.getContent();
    }

    private String convertWriter(User user, DeliveryPost deliveryPost){
        DeliveryCommentSeq deliveryCommentSeq = deliveryCommentSeqRepository.findByUserAndDeliveryPost(user, deliveryPost).orElse(null);
        int writeSeq = deliveryCommentSeq.getWriteSeq();
        if(writeSeq == 0){
            return "글 쓴 다람쥐";
        }
        else{
            return "익명의 다람쥐" + writeSeq;
        }
    }
}
