package season.blossom.dotori.roommatecomment;

import lombok.Data;
import season.blossom.dotori.user.User;

@Data
public class RoommateCommentRequestDto {
    private Long roommatePostId;
    private Long parentCommentId;
    private String content;
    private User writer;
    private Boolean isSecret;

    public RoommateComment toEntity(){
        return RoommateComment.builder()
                .content(content)
                .writer(writer)
                .isSecret(isSecret)
                .build();
    }
}
