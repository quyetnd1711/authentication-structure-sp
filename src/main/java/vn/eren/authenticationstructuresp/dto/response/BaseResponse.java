package vn.eren.authenticationstructuresp.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseResponse {

    String createDate;

    String createBy;

    String writeDate;

    String writeBy;
}
