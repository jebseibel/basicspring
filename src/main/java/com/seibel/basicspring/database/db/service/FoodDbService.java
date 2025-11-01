package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Food;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.FoodDb;
import com.seibel.basicspring.database.db.exception.DatabaseFailureException;
import com.seibel.basicspring.database.db.mapper.FoodMapper;
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

    public FoodDbService(FoodRepository repository, FoodMapper mapper) {
        super("FoodDb");
        this.repository = repository;
        this.mapper = mapper;
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
            return mapper.toModel(repository.save(entity));
        } catch (Exception e) {
            log.error("Failed to create food: {}", extid, e);
            throw new DatabaseFailureException("Failed to create food", e);
        }
    }

    public Food update(String extid, Food item) throws DatabaseFailureException {
        FoodDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseFailureException("Food not found: " + extid));
        existing.setUpdatedAt(LocalDateTime.now());
        existing.setName(item.getName());
        existing.setCategory(item.getCategory());
        existing.setSubcategory(item.getSubcategory());
        existing.setDescription(item.getDescription());
        existing.setServingType(item.getServingType());
        existing.setNutrition(item.getNutrition());
        existing.setNotes(item.getNotes());
        existing.setCrunch(item.getCrunch());
        existing.setPunch(item.getPunch());
        existing.setSweet(item.getSweet());
        existing.setSavory(item.getSavory());
        return mapper.toModel(repository.save(existing));
    }

    public boolean delete(String extid) throws DatabaseFailureException {
        FoodDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseFailureException("Food not found: " + extid));
        existing.setDeletedAt(LocalDateTime.now());
        existing.setActive(ActiveEnum.INACTIVE);
        repository.save(existing);
        return true;
    }

    public Food findByExtid(String extid) throws DatabaseFailureException {
        return mapper.toModel(repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseFailureException("Food not found: " + extid)));
    }

    public List<Food> findAll() {
        return mapper.toModelList(repository.findAll());
    }

    public List<Food> findByActive(ActiveEnum active) {
        return mapper.toModelList(repository.findByActive(active));
    }
}

