package season.blossom.dotori.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import season.blossom.dotori.delivery.DeliveryPost;
import season.blossom.dotori.deliverycomment.DeliveryComment;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true)
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private University university;
    private String name;
    private Integer age;

    private Boolean calling;
    private Boolean smoking;
    private Boolean eating;
    private Integer cleaningCycle;
    private Integer floor;
    private String sleepHabits;
    private Integer sleepTime;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @JsonIgnore
    @OneToMany(mappedBy = "writer")
    private List<DeliveryComment> deliveryComments;

    @JsonIgnore
    @ManyToMany(mappedBy = "matchedUsers")
    private List<DeliveryPost> matchedDelieveryPost;

    public User encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
        return this;
    }

}
