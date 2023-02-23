package season.blossom.dotori.roommate;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import season.blossom.dotori.delivery.DeliveryPostDto;
import season.blossom.dotori.user.User;

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
                .roommateStatus(roommatePostDto.getRoommateStatus())
                .build();

        return roommatePostRepository.save(roommatePost);
    }


    @Transactional
    public List<RoommatePostReturnDto> getList() {
        List<RoommatePost> roommatePosts = roommatePostRepository.findAll();
        List<RoommatePostReturnDto> roommatePostList = new ArrayList<>();

        for (RoommatePost roommatePost : roommatePosts) {
            RoommatePostReturnDto roommatePostDto = RoommatePostReturnDto.builder()
                    .id(roommatePost.getId())
                    .writer(roommatePost.getWriter().getEmail())
                    .age(roommatePost.getWriter().getAge())
                    .calling(roommatePost.getWriter().getCalling())
                    .smoking(roommatePost.getWriter().getSmoking())
                    .eating(roommatePost.getWriter().getEating())
                    .cleaningCycle(roommatePost.getWriter().getCleaningCycle())
                    .floor(roommatePost.getWriter().getFloor())
                    .sleepHabits(roommatePost.getWriter().getSleepHabits())
                    .sleepTime(roommatePost.getWriter().getSleepTime())
                    .title(roommatePost.getTitle())
                    .people(roommatePost.getPeople())
                    .dorm_name(roommatePost.getDorm_name())
                    .content(roommatePost.getContent())
                    .createdDate(roommatePost.getCreatedDate())
                    .modifiedDate(roommatePost.getModifiedDate())
                    .build();

            roommatePostList.add(roommatePostDto);
        }
        return roommatePostList;
    }

    @Transactional
    public List<RoommatePostReturnDto> getListFiltered() {
        List<RoommatePost> roommatePosts = roommatePostRepository.findAll();
        List<RoommatePostReturnDto> roommatePostList = new ArrayList<>();

        for (RoommatePost roommatePost : roommatePosts) {
            if (roommatePost.getRoommateStatus().toString().equals("MATCHING")) {
                RoommatePostReturnDto roommatePostDto = RoommatePostReturnDto.builder()
                        .id(roommatePost.getId())
                        .writer(roommatePost.getWriter().getEmail())
                        .age(roommatePost.getWriter().getAge())
                        .calling(roommatePost.getWriter().getCalling())
                        .smoking(roommatePost.getWriter().getSmoking())
                        .eating(roommatePost.getWriter().getEating())
                        .cleaningCycle(roommatePost.getWriter().getCleaningCycle())
                        .floor(roommatePost.getWriter().getFloor())
                        .sleepHabits(roommatePost.getWriter().getSleepHabits())
                        .sleepTime(roommatePost.getWriter().getSleepTime())
                        .title(roommatePost.getTitle())
                        .people(roommatePost.getPeople())
                        .dorm_name(roommatePost.getDorm_name())
                        .content(roommatePost.getContent())
                        .createdDate(roommatePost.getCreatedDate())
                        .modifiedDate(roommatePost.getModifiedDate())
                        .build();

                roommatePostList.add(roommatePostDto);
            } else continue;
        }
        return roommatePostList;
    }

    @Transactional
    public RoommatePostReturnDto getPost(Long id) {
        Optional<RoommatePost> roommatePostWrapper = roommatePostRepository.findById(id);
        RoommatePost roommatePost = roommatePostWrapper.get();

        RoommatePostReturnDto roommatePostDto = RoommatePostReturnDto.builder()
                .id(roommatePost.getId())
                .title(roommatePost.getTitle())
                .people(roommatePost.getPeople())
                .dorm_name(roommatePost.getDorm_name())
                .content(roommatePost.getContent())
                .writer(roommatePost.getWriter().getEmail())
                .createdDate(roommatePost.getCreatedDate())
                .modifiedDate(roommatePost.getModifiedDate())
                .build();

        return roommatePostDto;
    }

    @Transactional
    public RoommatePostDto updatePost(Long postId, RoommatePostDto roommatePostDto, Long userId) {
        Optional<RoommatePost> byId = roommatePostRepository.findById(postId);
        RoommatePost roommatePost = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));

        if (roommatePost.getWriter().getUserId().equals(userId)){
            roommatePost.setTitle(roommatePostDto.getTitle());
            roommatePost.setPeople(roommatePostDto.getPeople());
            roommatePost.setDorm_name(roommatePostDto.getDorm_name());
            roommatePost.setContent(roommatePostDto.getContent());
            roommatePost.setRoommateStatus(roommatePostDto.toEntity().getRoommateStatus());

            return roommatePostDto.builder()
                    .id(roommatePost.getId())
                    .build();
        }
        else {
            throw new IllegalStateException();
        }
    }


    @Transactional
    public void deletePost(Long postId, Long userId) {
        Optional<RoommatePost> byId = roommatePostRepository.findById(postId);
        RoommatePost roommatePost = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));

        if (roommatePost.getWriter().getUserId().equals(userId)){
            roommatePostRepository.deleteById(postId);
        }
        else {
            throw new IllegalStateException();
        }
    }

    @Transactional
    public List<RoommatePostReturnDto> getMyList(User user) {
        List<RoommatePost> roommatePosts = roommatePostRepository.findAll();
        List<RoommatePostReturnDto> roommatePostList = new ArrayList<>();

        for (RoommatePost roommatePost : roommatePosts) {
            if (roommatePost.getWriter().getEmail().equals(user.getEmail())) {
                RoommatePostReturnDto roommatePostDto = RoommatePostReturnDto.builder()
                        .id(roommatePost.getId())
                        .writer(roommatePost.getWriter().getEmail())
                        .age(roommatePost.getWriter().getAge())
                        .calling(roommatePost.getWriter().getCalling())
                        .smoking(roommatePost.getWriter().getSmoking())
                        .eating(roommatePost.getWriter().getEating())
                        .cleaningCycle(roommatePost.getWriter().getCleaningCycle())
                        .floor(roommatePost.getWriter().getFloor())
                        .sleepHabits(roommatePost.getWriter().getSleepHabits())
                        .sleepTime(roommatePost.getWriter().getSleepTime())
                        .title(roommatePost.getTitle())
                        .people(roommatePost.getPeople())
                        .dorm_name(roommatePost.getDorm_name())
                        .content(roommatePost.getContent())
                        .createdDate(roommatePost.getCreatedDate())
                        .modifiedDate(roommatePost.getModifiedDate())
                        .build();

                roommatePostList.add(roommatePostDto);
            } else continue;
        }
        return roommatePostList;
    }

}