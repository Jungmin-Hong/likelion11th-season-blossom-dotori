package season.blossom.dotori.roommatecomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoommateCommentReturnDto {
    private Long commentId;
    private String content;
    private String writer;
    private boolean isSecret;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<RoommateCommentReturnDto> childCommentList;
}
