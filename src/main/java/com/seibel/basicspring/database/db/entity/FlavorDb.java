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
@Table(name = "flavor")
public class FlavorDb extends BaseFoodDb {

    private static final long serialVersionUID = 8204156391023478112L;

    @Column(name = "`usage`", length = 16)
    private String usage;

    // Rating columns (1–5 range)
    @Min(1)
    @Max(5)
    @Column(name = "crunch", nullable = false, columnDefinition = "TINYINT CHECK (crunch BETWEEN 1 AND 5)")
    private Integer crunch;

    @Min(1)
    @Max(5)
    @Column(name = "punch", nullable = false, columnDefinition = "TINYINT CHECK (punch BETWEEN 1 AND 5)")
    private Integer punch;

    @Min(1)
    @Max(5)
    @Column(name = "sweet", nullable = false, columnDefinition = "TINYINT CHECK (sweet BETWEEN 1 AND 5)")
    private Integer sweet;

    @Min(1)
    @Max(5)
    @Column(name = "savory", nullable = false, columnDefinition = "TINYINT CHECK (savory BETWEEN 1 AND 5)")
    private Integer savory;
}

