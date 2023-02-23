package season.blossom.dotori.delivery;

import lombok.Builder;
import lombok.Data;
import season.blossom.dotori.deliverycomment.DeliveryCommentReturnDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DeliveryPostReturnDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<DeliveryCommentReturnDto> comments;
}
