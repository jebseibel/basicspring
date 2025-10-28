package com.seibel.basicspring.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseProfile {
    private String extid;
    private String nickname;
    private String fullname;
}
