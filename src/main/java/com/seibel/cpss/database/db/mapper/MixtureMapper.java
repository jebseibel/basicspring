package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Mixture;
import com.seibel.cpss.database.db.entity.MixtureDb;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MixtureMapper {

    public Mixture toModel(MixtureDb item) {
        if (Objects.isNull(item)) {
            return null;
        }

        Mixture mixture = new Mixture();
        mixture.setId(item.getId());
        mixture.setExtid(item.getExtid());
        mixture.setName(item.getName());
        mixture.setDescription(item.getDescription());
        mixture.setUserExtid(item.getUserExtid());
        mixture.setCreatedAt(item.getCreatedAt());
        mixture.setUpdatedAt(item.getUpdatedAt());
        mixture.setDeletedAt(item.getDeletedAt());
        mixture.setActive(item.getActive());

        return mixture;
    }

    public MixtureDb toDb(Mixture item) {
        if (Objects.isNull(item)) {
            return null;
        }

        MixtureDb mixtureDb = new MixtureDb();
        mixtureDb.setId(item.getId());
        mixtureDb.setExtid(item.getExtid());
        mixtureDb.setName(item.getName());
        mixtureDb.setDescription(item.getDescription());
        mixtureDb.setUserExtid(item.getUserExtid());
        mixtureDb.setCreatedAt(item.getCreatedAt());
        mixtureDb.setUpdatedAt(item.getUpdatedAt());
        mixtureDb.setDeletedAt(item.getDeletedAt());
        mixtureDb.setActive(item.getActive());

        return mixtureDb;
    }

    public List<Mixture> toModelList(List<MixtureDb> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<MixtureDb> toDbList(List<Mixture> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toDb).collect(Collectors.toList());
    }
}
