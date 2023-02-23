package season.blossom.dotori.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import season.blossom.dotori.user.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@ToString
@NoArgsConstructor
public class DeliveryPostDto {
    private Long id;
    @JsonIgnore
    private User writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    private DeliveryStatus deliveryStatus;

    public DeliveryPost toEntity(){
        DeliveryPost deliveryPost = DeliveryPost.builder()
//                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .deliveryStatus(deliveryStatus)
                .build();
        return deliveryPost;
    }

    @Builder
    public DeliveryPostDto(Long id, String title, String content, User writer, LocalDateTime createdDate, LocalDateTime modifiedDate, DeliveryStatus deliveryStatus) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.deliveryStatus = deliveryStatus;
    }
}