package com.seibel.cpss.database.db.entity;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.converter.ActiveEnumConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseDb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    @Column(name = "extid", length = 36, nullable = false, unique = true, columnDefinition = "VARCHAR(36) DEFAULT (UUID())")
    protected String extid;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    @Column(name = "active", columnDefinition = "INT DEFAULT 1")
    @Convert(converter = ActiveEnumConverter.class)
    protected ActiveEnum active;
}
