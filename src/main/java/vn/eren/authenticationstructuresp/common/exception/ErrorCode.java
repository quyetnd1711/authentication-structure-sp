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
    // TOO_MANY_REQUESTS
    TOO_MANY_REQUESTS(9997, "Rate limit exceeded", HttpStatus.TOO_MANY_REQUESTS),
    // SERVICE_UNAVAILABLE
    SERVICE_UNAVAILABLE(9996, "Service unavailable due to circuit breaker", HttpStatus.SERVICE_UNAVAILABLE),
    // BAD_REQUEST - 400
    BAD_REQUEST(9998, "Unknown error", HttpStatus.BAD_REQUEST),
    INVALID_SEARCH(10002, "Invalid search", HttpStatus.BAD_REQUEST),
    NOT_EXIST_USER(10003, "User does not exist", HttpStatus.BAD_REQUEST),
    USER_EXISTED(10004, "User already exists", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL(10005, "Invalid email", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(10006, "User does not exist", HttpStatus.BAD_REQUEST),
    NOT_EXIST_PERMISSION(10007, "Permission does not exist", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(10008, "Permission already exists", HttpStatus.BAD_REQUEST),
    NOT_EXIST_ROLE(10009, "Role does not exist", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(10010, "Role already exists", HttpStatus.BAD_REQUEST),
    NOT_DELETE_ROLE_ADMIN(10011, "Do not delete role ADMIN", HttpStatus.BAD_REQUEST),
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
