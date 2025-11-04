package com.seibel.basicspring.database.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base JPA entity for tables populated via CSV import.
 * Extends BaseDb with common CSV-related fields.
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseFoodDb extends BaseDb {

    @Column(name = "code", length = 8, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = 32, nullable = false, unique = true)
    private String name;

    @Column(name = "category", length = 32, nullable = false)
    private String category;

    @Column(name = "subcategory", length = 32, nullable = false)
    private String subcategory;

    @Column(name = "description")
    private String description;

    @Column(name = "notes")
    private String notes;
}
