package com.seibel.cpss.web.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RequestProfileUpdate extends BaseRequest {

    @NotEmpty(message = "The nickname is required.")
    @Size(max = 64, message = "The nickname must be at most 64 characters.")
    private String nickname;

    @NotEmpty(message = "The full name is required.")
    @Size(max = 128, message = "The full name must be at most 128 characters.")
    private String fullname;
}
