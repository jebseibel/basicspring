package com.seibel.cpss.web.response;

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
    private String howtouse;
    private Integer crunch;
    private Integer punch;
    private Integer sweet;
    private Integer savory;
}
