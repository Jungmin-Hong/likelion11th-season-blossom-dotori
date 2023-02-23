package season.blossom.dotori.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import season.blossom.dotori.roommate.RoommatePost;
import season.blossom.dotori.roommate.RoommatePostReturnDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
//@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(@Lazy UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //시큐리티 내부의 로그인 프로세스중 인증처리를 하는 메소드 중 하나
    //이 메서드를 오버라이드하여 데이터베이스의 유저 정보
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        return new CustomUserDetail(user);
    }

    public User registerUser(RegisterRequestDto registerRequestDto) {
        User user = User.builder()
                .email(registerRequestDto.getEmail())
                .password(registerRequestDto.getPassword())
                .university(University.valueOf(registerRequestDto.getUniversity().toUpperCase(Locale.ROOT)))
                .authority(Authority.ROLE_USER)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateInfo(User user, UserReturnDto userReturnDto) {
        Optional<User> byId = userRepository.findById(user.getUserId());
        User me = byId.orElseThrow(() -> new NullPointerException("해당 포스트가 존재하지 않습니다."));

        me.setAge(userReturnDto.getAge());
        me.setCalling(userReturnDto.getCalling());
        me.setSmoking(userReturnDto.getSmoking());
        me.setEating(userReturnDto.getEating());
        me.setCleaningCycle(userReturnDto.getCleaningCycle());
        me.setFloor(userReturnDto.getFloor());
        me.setSleepHabits(userReturnDto.getSleepHabits());
        me.setSleepTime(userReturnDto.getSleepTime());
        userRepository.save(me);

        return me;
    }

    @Transactional
    public UserReturnDto getUser(User user) {
        UserReturnDto userReturnDto = UserReturnDto.builder()
                .age(user.getAge())
                .calling(user.getCalling())
                .smoking(user.getSmoking())
                .eating(user.getEating())
                .cleaningCycle(user.getCleaningCycle())
                .floor(user.getFloor())
                .sleepHabits(user.getSleepHabits())
                .sleepTime(user.getSleepTime())
                .build();

        return userReturnDto;
    }

    @Transactional
    public User updatePwd(User user, PwdRequestDto pwdRequestDto) throws Exception {
        User me = userRepository.findById(user.getUserId()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if (me.matchPassword(passwordEncoder, pwdRequestDto.getOriginal())) {
            if (pwdRequestDto.getNewpwd1().equals(pwdRequestDto.getNewpwd2())) {
                me.updatePassword(passwordEncoder, pwdRequestDto.getNewpwd1());
            } else {
                throw new IllegalStateException("같은 비밀번호를 입력해주세요.");
            }
        }
        else {
            throw new IllegalStateException("비밀번호가 틀렸습니다.");
        }



        return me;
    }



}
