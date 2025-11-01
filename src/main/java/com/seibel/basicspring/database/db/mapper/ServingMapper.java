package com.seibel.basicspring.database.db.mapper;

import com.seibel.basicspring.common.domain.Serving;
import com.seibel.basicspring.database.db.entity.ServingDb;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ServingMapper {

    private final ModelMapper mapper = new ModelMapper();

    public Serving toModel(ServingDb db) {
        return db == null ? null : mapper.map(db, Serving.class);
    }

    public ServingDb toDb(Serving model) {
        return model == null ? null : mapper.map(model, ServingDb.class);
    }

    public List<Serving> toModelList(List<ServingDb> list) {
        return list == null ? null : list.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<ServingDb> toDbList(List<Serving> list) {
        return list == null ? null : list.stream().map(this::toDb).collect(Collectors.toList());
    }
}
