package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPostDto;
import season.blossom.dotori.delivery.DeliveryPostRepository;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class DeliveryPostService {
    private DeliveryPostRepository deliveryPostRepository;

    @Transactional
    public Long savePost(DeliveryPostDto deliveryPostDto) {
        return deliveryPostRepository.save(deliveryPostDto.toEntity()).getId();
    }

    @Transactional
    public List<DeliveryPostDto> getDeliveryPostList() {
        List<DeliveryPost> deliveryPosts = deliveryPostRepository.findAll();
        List<DeliveryPostDto> deliveryPostList = new ArrayList<>();

        for ( DeliveryPost deliveryPost : deliveryPosts) {
            DeliveryPostDto deliveryPostDto = DeliveryPostDto.builder()
                    .id(deliveryPost.getId())
                    .title(deliveryPost.getTitle())
                    .content(deliveryPost.getContent())
                    .writer(deliveryPost.getWriter())
                    .createdDate(deliveryPost.getCreatedDate())
                    .build();

            deliveryPostList.add(deliveryPostDto);
        }

        return deliveryPostList;
    }
}