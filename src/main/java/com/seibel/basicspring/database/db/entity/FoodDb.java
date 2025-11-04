package com.seibel.basicspring.database.db.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
public class FoodDb extends BaseDb {

    private static final long serialVersionUID = 4913572206441095325L;

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

    @Column(name = "notes", length = 1000)
    private String notes;

    // OneToOne relationships
    @OneToOne
    @JoinColumn(name = "flavor_id", referencedColumnName = "id")
    private FlavorDb flavor;

    @OneToOne
    @JoinColumn(name = "nutrition_id", referencedColumnName = "id")
    private NutritionDb nutrition;

    @OneToOne
    @JoinColumn(name = "serving_id", referencedColumnName = "id")
    private ServingDb serving;
}

