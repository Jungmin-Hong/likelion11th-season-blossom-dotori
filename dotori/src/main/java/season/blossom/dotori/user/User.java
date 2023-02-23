package season.blossom.dotori.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.deliverycomment.DeliveryComment;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private University university;
    private String name;
    // 성별
    private Boolean gender;
    // 나이
    private Integer age;
    // 기숙사명
    private String dorm;
    // 선호 층수
    private Integer floor;
    // 청소 주기
    private String cleaningCycle;
    // 소등 시간
    private String sleepTime;
    // 잠버릇
    private String sleepHabits;
    // 기숙사 이용시간
    private String useTime;
    // 본인 흡연
    private Boolean smoking;
    // 식사 가능 여부
    private Boolean eating;
    // 통화 가능 여부
    private Boolean calling;
    // 룸메 흡연 여부
    private Boolean smokeMate;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonIgnore
    @OneToMany(mappedBy = "writer")
    private List<DeliveryComment> deliveryComments;

    @JsonIgnore
    @ManyToMany(mappedBy = "matchedUsers")
    private List<DeliveryPost> matchedDelieveryPost;
    

    @OneToMany(mappedBy = "writer")
    private List<DeliveryPost> deliveryPosts;

    public User encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
        return this;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

}
