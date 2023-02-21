package season.blossom.dotori.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
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
    private Byte cleaningCycle;
    private Byte floor;
    private String sleepHabits;
    private Byte sleepTime;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public User encodePassword(PasswordEncoder passwordEncoder){
        password = passwordEncoder.encode(password);
        return this;
    }

    public User commonRegister(){
        authority = Authority.ROLE_USER;
        return this;
    }
}
