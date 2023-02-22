package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPostDto;
import season.blossom.dotori.delivery.DeliveryPostRepository;
import season.blossom.dotori.deliverycomment.DeliveryComment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DeliveryPostService {
    private DeliveryPostRepository deliveryPostRepository;

    @Transactional
    public DeliveryPost savePost(DeliveryPostDto deliveryPostDto) {

        DeliveryPost deliveryPost = DeliveryPost.builder()
                .writer(deliveryPostDto.getWriter())
                .title(deliveryPostDto.getTitle())
                .content(deliveryPostDto.getContent())
                .build();

        return deliveryPostRepository.save(deliveryPost);
    }


    @Transactional
    public List<DeliveryPostDto> getList() {
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
//
        return deliveryPostList;
//        return deliveryPosts;
    }

    @Transactional
    public DeliveryPostDto getPost(Long id) {
        Optional<DeliveryPost> deliveryPostWrapper = deliveryPostRepository.findById(id);
        DeliveryPost deliveryPost = deliveryPostWrapper.get();

        DeliveryPostDto deliveryPostDto = DeliveryPostDto.builder()
                .id(deliveryPost.getId())
                .title(deliveryPost.getTitle())
                .content(deliveryPost.getContent())
                .writer(deliveryPost.getWriter())
                .createdDate(deliveryPost.getCreatedDate())
                .build();

        return deliveryPostDto;
    }


    @Transactional
    public void deletePost(Long id) {
        deliveryPostRepository.deleteById(id);
    }
}