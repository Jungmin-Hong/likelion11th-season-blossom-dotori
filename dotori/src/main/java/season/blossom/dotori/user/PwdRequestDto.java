package season.blossom.dotori.user;

import lombok.Data;

@Data
public class PwdRequestDto {
    private String original;
    private String newpwd1;
    private String newpwd2;
}
