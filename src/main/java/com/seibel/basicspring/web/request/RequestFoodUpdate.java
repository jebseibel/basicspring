package com.seibel.basicspring.web.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestFoodUpdate extends BaseRequest {

    @Size(max = 8)
    private String code;

    @Size(max = 32)
    private String name;

    @Size(max = 32)
    private String category;

    @Size(max = 32)
    private String subcategory;

    @Size(max = 255)
    private String description;

    @Size(max = 16)
    private String servingType;

    @Size(max = 512)
    private String nutrition;

    @Size(max = 1000)
    private String notes;

    @Min(1) @Max(5)
    private Integer crunch;

    @Min(1) @Max(5)
    private Integer punch;

    @Min(1) @Max(5)
    private Integer sweet;

    @Min(1) @Max(5)
    private Integer savory;
}
