package season.blossom.dotori.error.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource does not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    WRONG_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "Please check your original password."),
    DIFFERENT_PASSWORD(HttpStatus.INTERNAL_SERVER_ERROR, "Please check if you've written the same password to change."),
    UNAUTHORIZED_USER(HttpStatus.FORBIDDEN, "This user is not the owner of this."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
