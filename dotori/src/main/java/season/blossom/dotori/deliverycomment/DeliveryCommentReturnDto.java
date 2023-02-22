package season.blossom.dotori.deliverycomment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import season.blossom.dotori.user.User;

import java.util.List;

@Data
@Builder
public class DeliveryCommentReturnDto {
    private Long deliveryCommentId;
    private String content;
    private String writer;
    private boolean isSecret;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeliveryCommentReturnDto> childCommentList;
}
