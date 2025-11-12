package com.seibel.cpss.web.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResponseMixture {
    private String extid;
    private String name;
    private String description;
    private String userExtid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
