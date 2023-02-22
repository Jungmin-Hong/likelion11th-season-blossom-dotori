package season.blossom.dotori.roommate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.delivery.DeliveryPostDto;
import season.blossom.dotori.delivery.DeliveryPostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Service
public class RoommatePostService {
    private RoommatePostRepository roommatePostRepository;

    @Transactional
    public RoommatePost savePost(RoommatePostDto roommatePostDto) {

        RoommatePost roommatePost = RoommatePost.builder()
                .writer(roommatePostDto.getWriter())
                .title(roommatePostDto.getTitle())
                .people(roommatePostDto.getPeople())
                .dorm_name(roommatePostDto.getDorm_name())
                .content(roommatePostDto.getContent())
                .build();

        return roommatePostRepository.save(roommatePost);
    }


    @Transactional
    public List<RoommatePostDto> getList() {
        List<RoommatePost> roommatePosts = roommatePostRepository.findAll();
        List<RoommatePostDto> roommatePostList = new ArrayList<>();

        for ( RoommatePost roommatePost : roommatePosts) {
            RoommatePostDto roommatePostDto = RoommatePostDto.builder()
                    .id(roommatePost.getId())
                    .title(roommatePost.getTitle())
                    .people(roommatePost.getPeople())
                    .dorm_name(roommatePost.getDorm_name())
                    .content(roommatePost.getContent())
                    .writer(roommatePost.getWriter())
                    .createdDate(roommatePost.getCreatedDate())
                    .modifiedDate(roommatePost.getModifiedDate())
                    .build();

            roommatePostList.add(roommatePostDto);
        }
        return roommatePostList;
    }

    @Transactional
    public RoommatePostDto getPost(Long id) {
        Optional<RoommatePost> roommatePostWrapper = roommatePostRepository.findById(id);
        RoommatePost roommatePost = roommatePostWrapper.get();

        RoommatePostDto roommatePostDto = RoommatePostDto.builder()
                .id(roommatePost.getId())
                .title(roommatePost.getTitle())
                .people(roommatePost.getPeople())
                .dorm_name(roommatePost.getDorm_name())
                .content(roommatePost.getContent())
                .writer(roommatePost.getWriter())
                .createdDate(roommatePost.getCreatedDate())
                .modifiedDate(roommatePost.getModifiedDate())
                .build();

        return roommatePostDto;
    }

    @Transactional
    public RoommatePostDto updatePost(Long postId, RoommatePostDto roommatePostDto) {
        Optional<RoommatePost> byId = roommatePostRepository.findById(postId);
        RoommatePost roommatePost = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));

        roommatePost.setTitle(roommatePostDto.getTitle());
        roommatePost.setContent(roommatePostDto.getContent());

        return roommatePostDto.builder()
                .id(roommatePost.getId())
                .build();
    }


    @Transactional
    public void deletePost(Long id) {
        roommatePostRepository.deleteById(id);
    }
}