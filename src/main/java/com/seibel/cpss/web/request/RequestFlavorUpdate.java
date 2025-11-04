package com.seibel.cpss.web.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestFlavorUpdate extends BaseRequest {

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
    private String howtouse;

    private Integer crunch;
    private Integer punch;
    private Integer sweet;
    private Integer savory;
}

