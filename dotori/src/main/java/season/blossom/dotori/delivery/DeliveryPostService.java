package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPostDto;
import season.blossom.dotori.delivery.DeliveryPostRepository;
import season.blossom.dotori.deliverycomment.DeliveryComment;
import season.blossom.dotori.roommate.RoommatePost;
import season.blossom.dotori.roommate.RoommatePostReturnDto;

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
    public List<DeliveryPostReturnDto> getList() {
        List<DeliveryPost> deliveryPosts = deliveryPostRepository.findAll();
        List<DeliveryPostReturnDto> deliveryPostList = new ArrayList<>();

        for ( DeliveryPost deliveryPost : deliveryPosts) {
            DeliveryPostReturnDto deliveryPostDto = DeliveryPostReturnDto.builder()
                    .id(deliveryPost.getId())
                    .title(deliveryPost.getTitle())
                    .content(deliveryPost.getContent())
                    .writer(deliveryPost.getWriter().getEmail())
                    .createdDate(deliveryPost.getCreatedDate())
                    .modifiedDate(deliveryPost.getModifiedDate())
                    .build();

            deliveryPostList.add(deliveryPostDto);
        }

        return deliveryPostList;
    }

    @Transactional
    public List<DeliveryPostReturnDto> getListFiltered() {
        List<DeliveryPost> deliveryPosts = deliveryPostRepository.findAll();
        List<DeliveryPostReturnDto> deliveryPostList = new ArrayList<>();

        for ( DeliveryPost deliveryPost : deliveryPosts) {
            if (deliveryPost.getDeliveryStatus().toString().equals("MATCHING")) {
                DeliveryPostReturnDto deliveryPostDto = DeliveryPostReturnDto.builder()
                        .id(deliveryPost.getId())
                        .title(deliveryPost.getTitle())
                        .content(deliveryPost.getContent())
                        .writer(deliveryPost.getWriter().getEmail())
                        .createdDate(deliveryPost.getCreatedDate())
                        .modifiedDate(deliveryPost.getModifiedDate())
                        .build();
                deliveryPostList.add(deliveryPostDto);
            }
            else {
                continue;
            }
        }
        return deliveryPostList;
    }

    @Transactional
    public DeliveryPostReturnDto getPost(Long id) {
        Optional<DeliveryPost> deliveryPostWrapper = deliveryPostRepository.findById(id);
        DeliveryPost deliveryPost = deliveryPostWrapper.get();

        DeliveryPostReturnDto deliveryPostDto = DeliveryPostReturnDto.builder()
                .id(deliveryPost.getId())
                .title(deliveryPost.getTitle())
                .content(deliveryPost.getContent())
                .writer(deliveryPost.getWriter().getEmail())
                .createdDate(deliveryPost.getCreatedDate())
                .modifiedDate(deliveryPost.getModifiedDate())
                .build();

        return deliveryPostDto;
    }

    @Transactional
    public DeliveryPostDto updatePost(Long postId, DeliveryPostDto deliveryPostDto) {
        Optional<DeliveryPost> byId = deliveryPostRepository.findById(postId);
        DeliveryPost deliveryPost = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));

        deliveryPost.setTitle(deliveryPostDto.getTitle());
        deliveryPost.setContent(deliveryPostDto.getContent());
        deliveryPost.setDeliveryStatus(deliveryPostDto.toEntity().getDeliveryStatus());

        return deliveryPostDto.builder()
                .id(deliveryPost.getId())
                .build();
    }


    @Transactional
    public void deletePost(Long id) {
        deliveryPostRepository.deleteById(id);
    }
}