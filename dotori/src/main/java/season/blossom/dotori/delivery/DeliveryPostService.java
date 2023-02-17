package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPostDto;
import season.blossom.dotori.delivery.DeliveryPostRepository;

@AllArgsConstructor
@Service
public class DeliveryPostService {
    private DeliveryPostRepository deliveryPostRepository;

    @Transactional
    public Long savePost(DeliveryPostDto deliveryPostDto) {
        return deliveryPostRepository.save(deliveryPostDto.toEntity()).getId();
    }
}