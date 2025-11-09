package com.seibel.cpss.database.db.service;

import com.seibel.cpss.common.domain.Nutrition;
import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.NutritionDb;
import com.seibel.cpss.database.db.exceptions.DatabaseAccessException;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.mapper.NutritionMapper;
import com.seibel.cpss.database.db.repository.NutritionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NutritionDbService extends BaseDbService {

    private final NutritionRepository repository;
    private final NutritionMapper mapper;

    public NutritionDbService(NutritionRepository repository, NutritionMapper mapper) {
        super("NutritionDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    public Nutrition create(Nutrition item) {
        try {
            NutritionDb entity = mapper.toDb(item);
            entity.setUpdatedAt(LocalDateTime.now());
            NutritionDb saved = repository.save(entity);
            log.info(createdMessage(saved.getExtid()));
            return mapper.toModel(saved);
        } catch (Exception e) {
            log.error(failedOperationMessage("create"), e);
            throw new DatabaseFailureException(failedOperationMessage("create"), e);
        }
    }

    public Nutrition update(String extid, Nutrition item) {
        try {
            var existing = repository.findByExtid(extid);
            if (existing.isEmpty()) {
                return null;
            }

            NutritionDb nutritionDb = existing.get();
            nutritionDb.setUpdatedAt(LocalDateTime.now());
            if (item.getName() != null) nutritionDb.setName(item.getName());
            if (item.getCategory() != null) nutritionDb.setCategory(item.getCategory());
            if (item.getSubcategory() != null) nutritionDb.setSubcategory(item.getSubcategory());
            if (item.getDescription() != null) nutritionDb.setDescription(item.getDescription());
            if (item.getNotes() != null) nutritionDb.setNotes(item.getNotes());
            if (item.getCarbohydrate() != null) nutritionDb.setCarbohydrate(item.getCarbohydrate());
            if (item.getFat() != null) nutritionDb.setFat(item.getFat());
            if (item.getProtein() != null) nutritionDb.setProtein(item.getProtein());
            if (item.getSugar() != null) nutritionDb.setSugar(item.getSugar());

            NutritionDb saved = repository.save(nutritionDb);
            log.info(updatedMessage(extid));
            return mapper.toModel(saved);
        } catch (Exception e) {
            log.error(failedOperationMessage("update", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("update"), e);
        }
    }

    public boolean delete(String extid) {
        LocalDateTime now = LocalDateTime.now();

        try {
            NutritionDb entity = repository.findByExtid(extid)
                    .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));
            entity.setActive(ActiveEnum.INACTIVE);
            entity.setDeletedAt(now);
            repository.save(entity);
            log.info(deletedMessage(extid));
            return true;
        } catch (Exception e) {
            log.error(failedOperationMessage("delete", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("delete"), e);
        }
    }

    public Nutrition findByExtid(String extid) {
        try {
            Optional<NutritionDb> db = repository.findByExtid(extid);
            return db.map(mapper::toModel)
                    .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));
        } catch (Exception e) {
            log.error(failedOperationMessage("find", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("find"), e);
        }
    }

    public List<Nutrition> findAll() {
        List<Nutrition> results = mapper.toModelList(repository.findAll());
        log.info(foundByActiveMessage("all", results.size()));
        return results;
    }

    public List<Nutrition> findByActive(ActiveEnum active) {
        List<Nutrition> results = mapper.toModelList(repository.findByActive(active));
        log.info(foundByActiveMessage(active.toString(), results.size()));
        return results;
    }
}