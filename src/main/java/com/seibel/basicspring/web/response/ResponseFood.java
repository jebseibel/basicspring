package com.seibel.basicspring.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFood {
    private String extid;
    private String code;
    private String name;
    private String category;
    private String subcategory;
    private String description;
    private String servingType;
    private String nutrition;
    private String notes;
    private Integer crunch;
    private Integer punch;
    private Integer sweet;
    private Integer savory;
}
