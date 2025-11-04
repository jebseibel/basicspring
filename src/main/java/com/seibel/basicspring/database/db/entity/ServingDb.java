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
public class ServingDb extends BaseFoodDb {

    private static final long serialVersionUID = 9057732190046815234L;

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

