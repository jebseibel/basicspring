package com.seibel.basicspring.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseServing {

    private String extid;
    private String code;
    private String name;
    private String category;
    private String subcategory;
    private String description;
    private Integer cup;
    private Integer quarter;
    private Integer tablespoon;
    private Integer teaspoon;
    private Integer gram;
}
