package com.seibel.cpss.web.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RequestFlavorCreate extends BaseRequest {

    @NotEmpty(message = "The code is required.")
    @Size(max = 8)
    private String code;

    @NotEmpty(message = "The name is required.")
    @Size(max = 32)
    private String name;

    @NotEmpty(message = "The category is required.")
    @Size(max = 32)
    private String category;

    @NotEmpty(message = "The subcategory is required.")
    @Size(max = 32)
    private String subcategory;

    @Size(max = 255)
    private String description;

    @Size(max = 16)
    private String usage;

    private Integer crunch;
    private Integer punch;
    private Integer sweet;
    private Integer savory;
}

