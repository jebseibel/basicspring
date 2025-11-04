package com.seibel.cpss.database.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "profile")
public class ProfileDb extends BaseDb {

    private static final long serialVersionUID = 2564419308159034781L;

    @Column(name = "nickname", length = 64, nullable = false)
    private String nickname;

    @Column(name = "fullname", length = 128, nullable = false)
    private String fullname;
}
