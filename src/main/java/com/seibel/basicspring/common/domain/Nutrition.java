package com.seibel.basicspring.common.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Nutrition extends BaseDomain {

    private String code;
    private String name;
    private String category;
    private String subcategory;
    private String description;
    private Integer carbohydrate;
    private Integer fat;
    private Integer protein;
    private Integer sugar;
}
