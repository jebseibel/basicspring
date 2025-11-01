package com.seibel.basicspring.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Food extends BaseDomain {

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
