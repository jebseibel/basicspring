package com.seibel.basicspring.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFlavor {
    private String extid;
    private String code;
    private String name;
    private String category;
    private String subcategory;
    private String description;
    private String usage;
}
