package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.deliverycomment.DeliveryCommentReturnDto;
import season.blossom.dotori.deliverycomment.DeliveryCommentService;
import season.blossom.dotori.user.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class DeliveryPostService {
    private DeliveryPostRepository deliveryPostRepository;
    private DeliveryCommentService deliveryCommentService;

    @Transactional
    public DeliveryPostReturnDto savePost(DeliveryPostDto deliveryPostDto) {

        DeliveryPost deliveryPost = DeliveryPost.builder()
                .writer(deliveryPostDto.getWriter())
                .title(deliveryPostDto.getTitle())
                .content(deliveryPostDto.getContent())
                .deliveryStatus(deliveryPostDto.getDeliveryStatus())
                .build();


        return new DeliveryPostReturnDto(deliveryPostRepository.save(deliveryPost));
    }


    @Transactional
    public List<DeliveryPostReturnDto> getList(User user, int matchType) {

        List<DeliveryPost> deliveryPosts;

        if(matchType == 1) {
            deliveryPosts = deliveryPostRepository.findAllByWriter_UniversityAndDeliveryStatusOrderByCreatedDateDesc(
                    user.getUniversity(), DeliveryStatus.MATCHING);
        }
        else {
            deliveryPosts = deliveryPostRepository.findAllByWriter_UniversityOrderByCreatedDateDesc(
                    user.getUniversity());
        }

        return deliveryPosts.stream().map(DeliveryPostReturnDto::new).collect(Collectors.toList());
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

    public DeliveryPostReturnDto getPost(Long postId, Long userId) {
        Optional<DeliveryPost> deliveryPostWrapper = deliveryPostRepository.findById(postId);
        DeliveryPost deliveryPost = deliveryPostWrapper.get();
        List<DeliveryCommentReturnDto> comments = deliveryCommentService.getComments(postId, userId);

        DeliveryPostReturnDto deliveryPostDto = DeliveryPostReturnDto.builder()
                .id(deliveryPost.getId())
                .title(deliveryPost.getTitle())
                .content(deliveryPost.getContent())
                .writer(deliveryPost.getWriter().getEmail())
                .createdDate(deliveryPost.getCreatedDate())
                .modifiedDate(deliveryPost.getModifiedDate())
                .comments(comments)
                .build();

        return deliveryPostDto;
    }

    @Transactional
    public DeliveryPostReturnDto getPost(Long postId) {
        Optional<DeliveryPost> deliveryPostWrapper = deliveryPostRepository.findById(postId);
        DeliveryPost deliveryPost = deliveryPostWrapper.get();

        DeliveryPostReturnDto deliveryPostDto = new DeliveryPostReturnDto(deliveryPost);

        return deliveryPostDto;
    }

    public DeliveryPostReturnDto postMatchStatus(Long postId, Long userId){
        DeliveryPost deliveryPost = deliveryPostRepository.findById(postId).orElse(null);
        if(deliveryPost.getWriter().getUserId() == userId){
            deliveryPost.setDeliveryStatus(DeliveryStatus.MATCHED);
        }

        DeliveryPostReturnDto deliveryPostDto = new DeliveryPostReturnDto(deliveryPost);

        return deliveryPostDto;
    }

    @Transactional
    public DeliveryPostDto updatePost(Long postId, DeliveryPostDto deliveryPostDto, Long userId) {
        Optional<DeliveryPost> byId = deliveryPostRepository.findById(postId);
        DeliveryPost deliveryPost = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));

        if (deliveryPost.getWriter().getUserId().equals(userId)){
            deliveryPost.setTitle(deliveryPostDto.getTitle());
            deliveryPost.setContent(deliveryPostDto.getContent());
            deliveryPost.setDeliveryStatus(deliveryPostDto.toEntity().getDeliveryStatus());

            return deliveryPostDto.builder()
                    .id(deliveryPost.getId())
                    .build();
        }
        else {
            throw new IllegalStateException();
        }

    }


    @Transactional
    public void deletePost(Long postId, Long userId) {
        Optional<DeliveryPost> byId = deliveryPostRepository.findById(postId);
        DeliveryPost deliveryPost = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));
        if (deliveryPost.getWriter().getUserId().equals(userId)){
            deliveryPostRepository.deleteById(postId);
        }
        else {
            throw new IllegalStateException();
        }

    }


    @Transactional
    public List<DeliveryPostReturnDto> getMyList(User user) {
        List<DeliveryPost> deliveryPosts = deliveryPostRepository.findAll();
        List<DeliveryPostReturnDto> deliveryPostList = new ArrayList<>();

        for ( DeliveryPost deliveryPost : deliveryPosts) {
            if (deliveryPost.getWriter().getEmail().equals(user.getEmail())) {
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
}