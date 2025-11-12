package com.seibel.cpss.web.response;

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
    private String notes;
    private ResponseFlavor flavor;
    private ResponseNutrition nutrition;
    private ResponseServing serving;
    private Boolean foundation;
    private Boolean mixable;
}
