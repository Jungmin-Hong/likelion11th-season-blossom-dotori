package season.blossom.dotori.user;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private String email;
    private String password;
    private String name;
    private Integer age;

    private Boolean calling;
    private Boolean smoking;
    private Boolean eating;
    private Integer cleaningCycle;
    private Integer floor;
    private String sleepHabits;
    private String sleepTime;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToMany(mappedBy = "writer")
    private List<DeliveryComment> deliveryComments;

    public User encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
        return this;
    }

    public User commonRegister(){
        authority = Authority.ROLE_USER;
        return this;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }
}
