package vn.eren.authenticationstructuresp.common.exception;

import jakarta.validation.ConstraintViolation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vn.eren.authenticationstructuresp.common.util.ValidateUtil;
import vn.eren.authenticationstructuresp.dto.response.ApiResponse;


import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handlingException(Exception ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatus(ErrorCode.BAD_REQUEST.getStatusCode().value());
        apiResponse.setCode(ErrorCode.BAD_REQUEST.getCode());
        apiResponse.setMessage(ErrorCode.BAD_REQUEST.getMessage());
        logger.error("=============> Exception: {}", ex.getMessage());
        return ResponseEntity.internalServerError().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse<?>> handlingMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        String enumKey = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        Map<String, Object> attributes =null;
        try {
            errorCode = ErrorCode.valueOf(enumKey);
            attributes = getAttributesFromConstraintViolation(ex);
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
        apiResponse.setHttpStatus(errorCode.getStatusCode().value());
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(Objects.nonNull(attributes) ?
                ValidateUtil.mapAttribute(errorCode.getMessage(), attributes) :
                errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handlingAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();
        ApiResponse<Object> apiResponse = new ApiResponse<>();
        apiResponse.setHttpStatus(errorCode.getStatusCode().value());
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getAttributesFromConstraintViolation(MethodArgumentNotValidException ex) {
        try {
            return ex.getBindingResult().getAllErrors().get(0)
                    .unwrap(ConstraintViolation.class)
                    .getConstraintDescriptor()
                    .getAttributes();
        } catch (Exception e) {
            logger.error("Error extracting attributes: {}", e.getMessage());
            return null;
        }
    }
}
