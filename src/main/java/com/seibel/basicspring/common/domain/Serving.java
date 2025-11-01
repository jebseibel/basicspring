package com.seibel.basicspring.common.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Serving extends BaseDomain {

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
