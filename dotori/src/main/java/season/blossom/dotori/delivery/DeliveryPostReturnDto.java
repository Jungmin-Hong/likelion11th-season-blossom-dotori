package season.blossom.dotori.delivery;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import season.blossom.dotori.deliverycomment.DeliveryCommentReturnDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DeliveryPostReturnDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private DeliveryStatus deliveryStatus;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeliveryCommentReturnDto> comments;

    public DeliveryPostReturnDto(DeliveryPost deliveryPost) {
        this.id = deliveryPost.getId();
        this.writer = deliveryPost.getWriter().getName();
        this.title = deliveryPost.getTitle();
        this.content = deliveryPost.getContent();
        this.createdDate = deliveryPost.getCreatedDate();
        this.modifiedDate = deliveryPost.getModifiedDate();
        this.deliveryStatus = deliveryPost.getDeliveryStatus();
    }
}
