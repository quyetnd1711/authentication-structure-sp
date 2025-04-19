package vn.eren.authenticationstructuresp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.experimental.FieldDefaults;
import vn.eren.authenticationstructuresp.config.instant.CustomInstantSerializer;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    @Builder.Default
    @JsonSerialize(using = CustomInstantSerializer.class)
    Instant time = Instant.now();

    int httpStatus;

    @Builder.Default
    int code = 1000;

    String message;

    T data;

}
