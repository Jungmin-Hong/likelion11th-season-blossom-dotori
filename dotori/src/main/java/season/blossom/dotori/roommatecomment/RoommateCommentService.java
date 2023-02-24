package season.blossom.dotori.roommatecomment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.roommate.RoommatePost;
import season.blossom.dotori.notice.NoticeRepository;
import season.blossom.dotori.notice.Notice;
import season.blossom.dotori.notice.NoticeType;
import season.blossom.dotori.notice.PostType;
import season.blossom.dotori.roommate.RoommatePostRepository;
import season.blossom.dotori.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RoommateCommentService {
    private final RoommatePostRepository roommatePostRepository;
    private final RoommateCommentRepository roommateCommentRepository;
    private final RoommateCommentSeqRepository roommateCommentSeqRepository;
    private final NoticeRepository noticeRepository;

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

        if(isSecret ^ commentDto.getIsSecret()){
            throw new IllegalStateException("일반 댓글에는 일반 답글만, 비밀 댓글에는 비밀 답글만 사용 가능");
        }

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

        RoommateCommentSeq foundSeq = roommateCommentSeqRepository.findByUserAndRoommatePost(commentDto.getWriter(), roommatePost).orElse(null);

        if(roommatePost.getWriter().getUserId() != commentDto.getWriter().getUserId()) {
            if(foundSeq == null) {
                roommatePost.increaseNumber();
                RoommateCommentSeq curSeq = RoommateCommentSeq.builder()
                        .writeSeq(roommatePost.getNumberOfCommentWriter())
                        .user(commentDto.getWriter())
                        .roommatePost(roommatePost)
                        .build();
                roommateCommentSeqRepository.save(curSeq);
            }
        }

        RoommateComment roommateComment = RoommateComment.builder()
                .roommatePost(roommatePost)
                .parentComment(parentComment)
                .writer(commentDto.getWriter())
                .content(commentDto.getContent())
                .isSecret(isSecret)
                .build();

        createNoticeForPostWriter(roommatePost, roommateComment);
        if(parentComment != null)
            createNoticeForCommentWriter(roommatePost, roommateComment, parentComment);

        return roommateCommentRepository.save(roommateComment);
    }

    private void createNoticeForCommentWriter(RoommatePost roommatePost, RoommateComment roommateComment, RoommateComment parentComment) {
        //원댓글 작성자가 자신이 아닌 경우에만 알람을 보내야함
        //원댓글자가 작성자일 때에는 원댓글 작성자 댓글에 답글을 달아도 답글 알림을 생성하지 않음
        //자신의 게시글에 댓글 달린것은 알림이 이미 생성되었기 때문
        if(roommateComment.getWriter().getUserId() != parentComment.getWriter().getUserId() &&
                parentComment.getWriter().getUserId() != roommatePost.getWriter().getUserId()){
            Notice notice = Notice.builder()
                    .user(parentComment.getWriter())
                    .noticeType(NoticeType.REPLY)
                    .postType(PostType.DELIVERY)
                    .roommatePost(roommatePost)
                    .content(roommateComment.getContent())
                    .build();
            noticeRepository.save(notice);
        }
    }

    private void createNoticeForPostWriter(RoommatePost roommatePost, RoommateComment roommateComment) {
        //본인 게시글에 댓글을 작성할 때에는 알림이 생성되지 않아야 함
        if(roommatePost.getWriter().getUserId() != roommateComment.getWriter().getUserId()){
            Notice notice = Notice.builder()
                    .user(roommatePost.getWriter())
                    .noticeType(NoticeType.COMMENT)
                    .postType(PostType.DELIVERY)
                    .roommatePost(roommatePost)
                    .content(roommateComment.getContent())
                    .build();
            noticeRepository.save(notice);
        }
    }

    public List<RoommateCommentReturnDto> getComments(Long postId, Long userId) {
        List<RoommateComment> comments = roommateCommentRepository.findByRoommatePostIdAndParentCommentIsNull(postId);
        RoommatePost roommatePost = roommatePostRepository.findById(postId).orElse(null);

        List<RoommateCommentReturnDto> returnDtos = comments.stream()
                .map(comment -> {
                    List<RoommateCommentReturnDto> childCommentDtos = comment.getChildComment().stream()
                            .map(child -> {
                                String content = filterContent(userId, child);
                                String writerAlias = convertWriter(child.getWriter(), roommatePost);
                                RoommateCommentReturnDto returnDto = RoommateCommentReturnDto.builder()
                                        .commentId(child.getId())
                                        .content(content)
                                        .writer(writerAlias)
                                        .isSecret(child.isSecret())
                                        .build();
                                return returnDto;
                            })
                            .collect(Collectors.toList());
                    return RoommateCommentReturnDto.builder()
                            .commentId(comment.getId())
                            .content(filterContent(userId, comment))
                            .writer(convertWriter(comment.getWriter(), roommatePost))
                            .isSecret(comment.isSecret())
                            .childCommentList(childCommentDtos.isEmpty() ? null : childCommentDtos)
                            .build();
                })
                .collect(Collectors.toList());
        return returnDtos;
    }

    private String filterContent(Long userId, RoommateComment roommateComment){
        if(roommateComment.isSecret()){
            if(!(roommateComment.getRoommatePost().getWriter().getUserId() == userId ||
                    roommateComment.getWriter().getUserId() == userId))
            return "비밀댓글입니다.";
        }
        return roommateComment.getContent();
    }

    private String convertWriter(User user, RoommatePost roommatePost){
        RoommateCommentSeq roommateCommentSeq = roommateCommentSeqRepository.findByUserAndRoommatePost(user, roommatePost).orElse(null);
        int writeSeq = roommateCommentSeq.getWriteSeq();
        if(writeSeq == 0){
            return "글 쓴 다람쥐";
        }
        else{
            return "익명의 다람쥐" + writeSeq;
        }
    }
}
