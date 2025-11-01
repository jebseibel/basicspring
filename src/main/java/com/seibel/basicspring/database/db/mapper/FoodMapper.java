package com.seibel.basicspring.database.db.mapper;

import com.seibel.basicspring.common.domain.Food;
import com.seibel.basicspring.database.db.entity.FoodDb;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class FoodMapper {

    private final ModelMapper mapper = new ModelMapper();

    public Food toModel(FoodDb item) {
        return Objects.isNull(item) ? null : mapper.map(item, Food.class);
    }

    public FoodDb toDb(Food item) {
        return Objects.isNull(item) ? null : mapper.map(item, FoodDb.class);
    }

    public List<Food> toModelList(List<FoodDb> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toModel).collect(Collectors.toList());
    }

    public List<FoodDb> toDbList(List<Food> items) {
        return Objects.isNull(items) ? List.of() :
                items.stream().map(this::toDb).collect(Collectors.toList());
    }
}
