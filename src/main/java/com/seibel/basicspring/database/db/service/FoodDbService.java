package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Food;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.FoodDb;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
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