package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Flavor;
import com.seibel.cpss.database.db.entity.FlavorDb;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FlavorMapper {

    private final ModelMapper mapper = new ModelMapper();

    public Flavor toModel(FlavorDb item) {
        return Objects.isNull(item) ? null : mapper.map(item, Flavor.class);
    }

    public FlavorDb toDb(Flavor item) {
        return Objects.isNull(item) ? null : mapper.map(item, FlavorDb.class);
    }

    public List<Flavor> toModelList(List<FlavorDb> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<FlavorDb> toDbList(List<Flavor> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toDb).collect(Collectors.toList());
    }
}
