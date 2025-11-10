package com.seibel.cpss.database.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
public class FoodDb extends BaseFoodDb {

    private static final long serialVersionUID = 4913572206441095325L;

    @Column(name = "category", length = 32, nullable = false)
    private String category;

    @Column(name = "subcategory", length = 32, nullable = false)
    private String subcategory;

    @Column(name = "flavor", length = 32)
    private String flavor;

    @Column(name = "nutrition", length = 32)
    private String nutrition;

    @Column(name = "serving", length = 32)
    private String serving;

    @Column(name = "foundation", nullable = false)
    private Boolean foundation = false;

}

