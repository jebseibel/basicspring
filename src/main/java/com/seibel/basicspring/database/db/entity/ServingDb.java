package com.seibel.basicspring.database.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "serving")
public class ServingDb extends BaseDb {

    private static final long serialVersionUID = 9057732190046815234L;

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

    @Column(name = "cup")
    private Integer cup;

    @Column(name = "quarter")
    private Integer quarter;

    @Column(name = "tablespoon")
    private Integer tablespoon;

    @Column(name = "teaspoon")
    private Integer teaspoon;

    @Column(name = "gram")
    private Integer gram;
}

