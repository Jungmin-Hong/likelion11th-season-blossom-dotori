package season.blossom.dotori.delivery;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DeliveryPostDto {
    private Long id;
    private String writer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public DeliveryPost toEntity(){
        DeliveryPost deliveryPost = DeliveryPost.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return deliveryPost;
    }

    @Builder
    public DeliveryPostDto(Long id, String title, String content, String writer, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}