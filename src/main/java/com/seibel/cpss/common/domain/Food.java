package com.seibel.cpss.common.domain;

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
    private String notes;
    private Flavor flavor;
    private Nutrition nutrition;
    private Serving serving;
    private Boolean foundation;
}
