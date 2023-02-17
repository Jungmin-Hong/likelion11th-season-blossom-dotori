package season.blossom.dotori.delivery;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "DeliveryPost")
@Getter @Setter
@ToString
@NoArgsConstructor
public class DeliveryPost extends TimeEntity {

    // 배달 게시글 번호
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 매핑 필요
//    private Long writer_id;
    @Column(nullable = false, length = 10)
    private String writer;

    @Column(nullable = false, length = 30) // 제한 없이도 가능
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    // 등록 시간
//    private LocalDateTime regTime;

    // 매칭 상태
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;


    @Builder
    public DeliveryPost(Long id, String writer, String title, String content) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.deliveryStatus = DeliveryStatus.MATCHING;
    }

}
