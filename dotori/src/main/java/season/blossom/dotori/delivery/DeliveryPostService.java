package season.blossom.dotori.delivery;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.deliverycomment.DeliveryCommentReturnDto;
import season.blossom.dotori.deliverycomment.DeliveryCommentSeq;
import season.blossom.dotori.deliverycomment.DeliveryCommentSeqRepository;
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
    private DeliveryCommentSeqRepository deliveryCommentSeqRepository;
    private DeliveryPostRepository deliveryPostRepository;
    private DeliveryCommentService deliveryCommentService;

    @Transactional
    public DeliveryPostReturnDto savePost(DeliveryPostDto deliveryPostDto) {

        DeliveryPost deliveryPost = DeliveryPost.builder()
                .writer(deliveryPostDto.getWriter())
                .title(deliveryPostDto.getTitle())
                .store(deliveryPostDto.getStore())
                .place(deliveryPostDto.getPlace())
                .amount(deliveryPostDto.getAmount())
                .minimum(deliveryPostDto.getMinimum())
                .content(deliveryPostDto.getContent())
                .deliveryStatus(deliveryPostDto.getDeliveryStatus())
                .numberOfCommentWriter(0)
                .build();

        DeliveryPost savedPost = deliveryPostRepository.save(deliveryPost);
        DeliveryCommentSeq deliveryCommentSeq = DeliveryCommentSeq.builder()
                .deliveryPost(savedPost)
                .user(deliveryPost.getWriter())
                .writeSeq(savedPost.getNumberOfCommentWriter())
                .build();

        deliveryCommentSeqRepository.save(deliveryCommentSeq);

        return new DeliveryPostReturnDto(savedPost);
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

    public DeliveryPostReturnDto getPost(Long postId, Long userId) {
        Optional<DeliveryPost> deliveryPostWrapper = deliveryPostRepository.findById(postId);
        DeliveryPost deliveryPost = deliveryPostWrapper.get();
        List<DeliveryCommentReturnDto> comments = deliveryCommentService.getComments(postId, userId);

        DeliveryPostReturnDto deliveryPostDto = DeliveryPostReturnDto.builder()
                .id(deliveryPost.getId())
                .title(deliveryPost.getTitle())
                .store(deliveryPost.getStore())
                .place(deliveryPost.getPlace())
                .amount(deliveryPost.getAmount())
                .minimum(deliveryPost.getAmount())
                .content(deliveryPost.getContent())
                .writer(deliveryPost.getWriter().getEmail())
                .createdDate(deliveryPost.getCreatedDate())
                .modifiedDate(deliveryPost.getModifiedDate())
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
            deliveryPost.setId(deliveryPost.getId());
            deliveryPost.setWriter(deliveryPost.getWriter());
            deliveryPost.setTitle(deliveryPostDto.getTitle());
            deliveryPost.setContent(deliveryPostDto.getContent());

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
                        .store(deliveryPost.getStore())
                        .place(deliveryPost.getPlace())
                        .amount(deliveryPost.getAmount())
                        .minimum(deliveryPost.getAmount())
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

    public List<DeliveryPostReturnDto> getMyCommentList(User user) {
        return deliveryPostRepository.findAllByCommentWriter(user)
                .stream().map(DeliveryPostReturnDto::new)
                .collect(Collectors.toList());
    }

    public DeliveryPostReturnDto getPostForUpdate(Long no, User user) {
        Optional<DeliveryPost> deliveryPostWrapper = deliveryPostRepository.findById(no);
        DeliveryPost deliveryPost = deliveryPostWrapper.get();

        if(deliveryPost.getWriter().getUserId() != user.getUserId()){
            throw new IllegalStateException();
        }

        return new DeliveryPostReturnDto(deliveryPost);
    }
}