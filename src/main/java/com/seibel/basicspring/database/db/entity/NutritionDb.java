package com.seibel.basicspring.database.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "nutrition")
public class NutritionDb extends BaseDb {

    private static final long serialVersionUID = 6732009184148857729L;

    @Column(name = "code", length = 8, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = 32, nullable = false, unique = true)
    private String name;

    @Column(name = "category", length = 32, nullable = false)
    private String category;

    @Column(name = "subcategory", length = 32, nullable = false)
    private String subcategory;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "carbohydrate", nullable = false)
    private Integer carbohydrate;

    @Column(name = "fat", nullable = false)
    private Integer fat;

    @Column(name = "protein", nullable = false)
    private Integer protein;

    @Column(name = "sugar", nullable = false)
    private Integer sugar;
}

