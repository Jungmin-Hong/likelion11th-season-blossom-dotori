package season.blossom.dotori.user;


import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import season.blossom.dotori.error.errorcode.CommonErrorCode;
import season.blossom.dotori.error.exception.RestApiException;
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
//            throw new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND);
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
        UserReturnDto userReturnDto = new UserReturnDto(user);

        return userRepository.save(user);
    }

    @Transactional
    public User updateInfo(User user, UserReturnDto userReturnDto) {
        Optional<User> byId = userRepository.findById(user.getUserId());
        User me = byId.orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        me.setGender(userReturnDto.getGender());
        me.setAge(userReturnDto.getAge());
        me.setDorm(userReturnDto.getDorm());
        me.setFloor(userReturnDto.getFloor());
        me.setCleaningCycle(userReturnDto.getCleaningCycle());
        me.setSleepTime(userReturnDto.getSleepTime());
        me.setSleepHabits(userReturnDto.getSleepHabits());
        me.setUseTime(userReturnDto.getUseTime());
        me.setSmoking(userReturnDto.getSmoking());
        me.setEating(userReturnDto.getEating());
        me.setCalling(userReturnDto.getCalling());
        me.setSmokeMate(userReturnDto.getSmokeMate());
        userRepository.save(me);

        return me;
    }

    @Transactional
    public UserReturnDto getUser(User user) {
        Optional<User> byId = userRepository.findById(user.getUserId());
        User me = byId.orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        UserReturnDto userReturnDto = UserReturnDto.builder()
                .gender(me.getGender())
                .age(me.getAge())
                .dorm(me.getDorm())
                .floor(me.getFloor())
                .cleaningCycle(me.getCleaningCycle())
                .sleepTime(me.getSleepTime())
                .sleepHabits(me.getSleepHabits())
                .useTime(me.getUseTime())
                .smoking(me.getSmoking())
                .eating(me.getEating())
                .calling(me.getCalling())
                .smokeMate(me.getSmokeMate())
                .build();

        return userReturnDto;
    }

    @Transactional
    public User updatePwd(User user, PwdRequestDto pwdRequestDto){
        Optional<User> byId = userRepository.findById(user.getUserId());
        User me = byId.orElseThrow(() -> new RestApiException(CommonErrorCode.RESOURCE_NOT_FOUND));

        if (me.matchPassword(passwordEncoder, pwdRequestDto.getOriginal())) {
            if (pwdRequestDto.getNewpwd1().equals(pwdRequestDto.getNewpwd2())) {
                me.updatePassword(passwordEncoder, pwdRequestDto.getNewpwd1());
            } else {
                throw new RestApiException(CommonErrorCode.DIFFERENT_PASSWORD);
            }
        }
        else {
            throw new RestApiException(CommonErrorCode.WRONG_PASSWORD);
        }
        return me;
    }

}
