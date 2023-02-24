package season.blossom.dotori.error.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import season.blossom.dotori.error.errorcode.ErrorCode;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

    private final ErrorCode errorCode;

}
