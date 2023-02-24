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
    private String store;
    private String place;
    private Integer amount;
    private Integer minimum;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private MatchingStatus matchingStatus;

    public DeliveryPost toEntity(){
        DeliveryPost deliveryPost = DeliveryPost.builder()
//                .id(id)
                .writer(writer)
                .title(title)
                .store(store)
                .place(place)
                .amount(amount)
                .minimum(minimum)
                .content(content)
                .build();
        return deliveryPost;
    }

    @Builder
    public DeliveryPostDto(Long id, String title, String store, String place, Integer amount, Integer minimum, String content, User writer, LocalDateTime createdDate, LocalDateTime modifiedDate, MatchingStatus matchingStatus) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.store = store;
        this.place = place;
        this.amount = amount;
        this.minimum = minimum;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.matchingStatus = matchingStatus;
    }
}