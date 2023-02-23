package season.blossom.dotori.roommatecomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.roommate.RoommatePost;
import season.blossom.dotori.roommate.RoommatePostRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoommateCommentService {
    private final RoommatePostRepository roommatePostRepository;
    private final RoommateCommentRepository roommateCommentRepository;

    public RoommateComment createComment(RoommateCommentRequestDto commentDto){

        RoommatePost roommatePost = roommatePostRepository.findById(commentDto.getRoommatePostId()).orElse(null);
        RoommateComment parentComment = null;
        boolean isSecret;

        if(commentDto.getParentCommentId() != null) {
            parentComment = roommateCommentRepository.findById(commentDto.getParentCommentId()).orElse(null);
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
            Long postWriterId = roommatePost.getWriter().getUserId();
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

        RoommateComment roommateComment = RoommateComment.builder()
                .roommatePost(roommatePost)
                .parentComment(parentComment)
                .writer(commentDto.getWriter())
                .content(commentDto.getContent())
                .isSecret(isSecret)
                .build();

        return roommateCommentRepository.save(roommateComment);
    }

    public List<RoommateCommentReturnDto> getComments(Long postId, Long userId) {
        List<RoommateComment> comments = roommateCommentRepository.findByRoommatePostIdAndParentCommentIsNull(postId);

        List<RoommateCommentReturnDto> returnDtos = comments.stream()
                .map(comment -> {
                    List<RoommateCommentReturnDto> childCommentDtos = comment.getChildComment().stream()
                            .map(child -> {
                                String content = filterContent(userId, child);
                                RoommateCommentReturnDto returnDto = RoommateCommentReturnDto.builder()
                                        .commentId(child.getId())
                                        .content(content)
                                        .writer(child.getWriter().getEmail())
                                        .isSecret(child.isSecret())
                                        .build();
                                return returnDto;
                            })
                            .collect(Collectors.toList());
                    return RoommateCommentReturnDto.builder()
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


        private String filterContent(Long userId, RoommateComment deliveryComment){
        if(deliveryComment.isSecret()){
            if(!(deliveryComment.getRoommatePost().getWriter().getUserId() == userId ||
                    deliveryComment.getWriter().getUserId() == userId))
            return "비밀댓글입니다.";
        }
        return deliveryComment.getContent();
    }
}
