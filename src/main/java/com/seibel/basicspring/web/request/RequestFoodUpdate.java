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

    @Size(max = 1000)
    private String notes;

    private String flavorExtid;
    private String nutritionExtid;
    private String servingExtid;
}
