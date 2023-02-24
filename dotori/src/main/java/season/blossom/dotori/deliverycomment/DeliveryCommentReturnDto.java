package season.blossom.dotori.deliverycomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeliveryCommentReturnDto {
    private Long commentId;
    private String content;
    private String writer;
    private boolean isSecret;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeliveryCommentReturnDto> childCommentList;
}
