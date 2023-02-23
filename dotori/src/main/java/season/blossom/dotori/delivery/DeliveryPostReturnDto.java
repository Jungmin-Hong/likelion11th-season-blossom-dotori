package season.blossom.dotori.delivery;

import lombok.Builder;
import lombok.Data;
import season.blossom.dotori.user.User;

import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryPostReturnDto {

    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

}
