package season.blossom.dotori.user;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String email;
    private String password;
    private String university;
}
