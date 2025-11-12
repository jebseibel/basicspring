package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Salad;
import com.seibel.cpss.database.db.entity.SaladDb;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class SaladMapper {

    public Salad toModel(SaladDb item) {
        if (Objects.isNull(item)) {
            return null;
        }

        Salad salad = new Salad();
        salad.setId(item.getId());
        salad.setExtid(item.getExtid());
        salad.setName(item.getName());
        salad.setDescription(item.getDescription());
        salad.setUserExtid(item.getUserExtid());
        salad.setCreatedAt(item.getCreatedAt());
        salad.setUpdatedAt(item.getUpdatedAt());
        salad.setDeletedAt(item.getDeletedAt());
        salad.setActive(item.getActive());

        return salad;
    }

    public SaladDb toDb(Salad item) {
        if (Objects.isNull(item)) {
            return null;
        }

        SaladDb saladDb = new SaladDb();
        saladDb.setId(item.getId());
        saladDb.setExtid(item.getExtid());
        saladDb.setName(item.getName());
        saladDb.setDescription(item.getDescription());
        saladDb.setUserExtid(item.getUserExtid());
        saladDb.setCreatedAt(item.getCreatedAt());
        saladDb.setUpdatedAt(item.getUpdatedAt());
        saladDb.setDeletedAt(item.getDeletedAt());
        saladDb.setActive(item.getActive());

        return saladDb;
    }

    public List<Salad> toModelList(List<SaladDb> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<SaladDb> toDbList(List<Salad> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toDb).collect(Collectors.toList());
    }
}
