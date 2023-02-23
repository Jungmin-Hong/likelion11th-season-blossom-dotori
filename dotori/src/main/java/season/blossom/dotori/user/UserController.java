package season.blossom.dotori.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;
import season.blossom.dotori.delivery.DeliveryPostReturnDto;
import season.blossom.dotori.roommate.RoommatePostDto;
import season.blossom.dotori.roommate.RoommatePostReturnDto;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequestDto authRequestDto) {
        User user = User.builder()
                .email(authRequestDto.getEmail())
                .password(authRequestDto.getPassword())
                .build();

        user.encodePassword(passwordEncoder);

        User registeredUser = userService.registerUser(user);

        return new ResponseEntity<>(registeredUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody LoginRequestDto loginRequestDto){
        UserDetails userDetails = userService.loadUserByUsername(loginRequestDto.getEmail());
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails, loginRequestDto.getPassword(), new ArrayList<>());
        try {
            authenticationManager.authenticate(authentication);
        }
        catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute
                (HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                        SecurityContextHolder.getContext());

        Cookie cookie = new Cookie("JSESSIONID", session.getId());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);
        return ResponseEntity.ok(authentication.getPrincipal());
    }

    @PutMapping("/mypage/edit")
    public ResponseEntity<UserReturnDto> updateUserInfo(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody UserReturnDto userReturnDto) {
        User user = userService.updateInfo(customUserDetail.getUser(), userReturnDto);

        UserReturnDto userReturn = userService.getUser(user);

        return ResponseEntity.ok(userReturn);
    }

    @PostMapping("/mypage/changepw")
    public ResponseEntity<HttpStatus> changePwd(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody PwdRequestDto pwdRequestDto) {
        try {
            userService.updatePwd(customUserDetail.getUser(),pwdRequestDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
