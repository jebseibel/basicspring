package com.seibel.basicspring.web.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestServingUpdate extends BaseRequest {

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

    private Integer cup;
    private Integer quarter;
    private Integer tablespoon;
    private Integer teaspoon;
    private Integer gram;
}
