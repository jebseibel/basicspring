package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Food;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.FoodDb;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.db.mapper.FoodMapper;
import com.seibel.basicspring.database.db.mapper.FlavorMapper;
import com.seibel.basicspring.database.db.mapper.NutritionMapper;
import com.seibel.basicspring.database.db.mapper.ServingMapper;
import com.seibel.basicspring.database.db.repository.FoodRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FoodDbService extends BaseDbService {

    private final FoodRepository repository;
    private final FoodMapper mapper;
    private final FlavorMapper flavorMapper;
    private final NutritionMapper nutritionMapper;
    private final ServingMapper servingMapper;

    public FoodDbService(FoodRepository repository, FoodMapper mapper,
                         FlavorMapper flavorMapper, NutritionMapper nutritionMapper,
                         ServingMapper servingMapper) {
        super("FoodDb");
        this.repository = repository;
        this.mapper = mapper;
        this.flavorMapper = flavorMapper;
        this.nutritionMapper = nutritionMapper;
        this.servingMapper = servingMapper;
    }

    public Food create(Food item) throws DatabaseFailureException {
        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            FoodDb entity = mapper.toDb(item);
            entity.setExtid(extid);
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            entity.setActive(ActiveEnum.ACTIVE);
            FoodDb saved = repository.save(entity);
            log.info(createdMessage(extid));
            return mapper.toModel(saved);
        } catch (Exception e) {
            log.error(failedOperationMessage("create", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("create"), e);
        }
    }

    public Food update(String extid, Food item) throws DatabaseFailureException {
        FoodDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseFailureException(notFoundMessage(extid)));

        existing.setUpdatedAt(LocalDateTime.now());
        if (item.getName() != null) existing.setName(item.getName());
        if (item.getCategory() != null) existing.setCategory(item.getCategory());
        if (item.getSubcategory() != null) existing.setSubcategory(item.getSubcategory());
        if (item.getDescription() != null) existing.setDescription(item.getDescription());
        if (item.getNotes() != null) existing.setNotes(item.getNotes());
        if (item.getFlavor() != null) existing.setFlavor(flavorMapper.toDb(item.getFlavor()));
        if (item.getNutrition() != null) existing.setNutrition(nutritionMapper.toDb(item.getNutrition()));
        if (item.getServing() != null) existing.setServing(servingMapper.toDb(item.getServing()));

        FoodDb saved = repository.save(existing);
        log.info(updatedMessage(extid));
        return mapper.toModel(saved);
    }

    public boolean delete(String extid) throws DatabaseFailureException {
        FoodDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseFailureException(notFoundMessage(extid)));

        existing.setDeletedAt(LocalDateTime.now());
        existing.setActive(ActiveEnum.INACTIVE);
        repository.save(existing);
        log.info(deletedMessage(extid));
        return true;
    }

    public Food findByExtid(String extid) throws DatabaseFailureException {
        return mapper.toModel(repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseFailureException(notFoundMessage(extid))));
    }

    public List<Food> findAll() {
        List<Food> results = mapper.toModelList(repository.findAll());
        log.info(foundByActiveMessage("all", results.size()));
        return results;
    }

    public List<Food> findByActive(ActiveEnum active) {
        List<Food> results = mapper.toModelList(repository.findByActive(active));
        log.info(foundByActiveMessage(active.toString(), results.size()));
        return results;
    }
}