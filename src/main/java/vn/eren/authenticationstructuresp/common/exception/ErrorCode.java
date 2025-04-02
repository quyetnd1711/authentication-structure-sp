package vn.eren.authenticationstructuresp.common.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode{
    // INTERNAL_SERVER_ERROR
    INVALID_KEY(9999, "Invalid value", HttpStatus.INTERNAL_SERVER_ERROR),
    // BAD_REQUEST - 400
    BAD_REQUEST(9998, "Unknown error", HttpStatus.BAD_REQUEST),
    INVALID_SEARCH(10002, "Invalid search", HttpStatus.BAD_REQUEST),
    // FORBIDDEN - 403
    UNAUTHORIZED(10000, "Unauthorized", HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED(10001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    // NOT_FOUND - 404
    // UNAUTHORIZED - 401
    ;

    int code;

    String message;

    HttpStatus statusCode;
}
