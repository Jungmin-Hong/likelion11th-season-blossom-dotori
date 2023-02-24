package season.blossom.dotori.deliverycomment;

import lombok.Data;
import season.blossom.dotori.user.User;

@Data
public class DeliveryCommentRequestDto {
    private Long deliveryPostId;
    private Long parentCommentId;
    private String content;
    private User writer;
    private Boolean isSecret;

    public DeliveryComment toEntity(){
        return DeliveryComment.builder()
                .content(content)
                .writer(writer)
                .isSecret(isSecret)
                .build();
    }
}
