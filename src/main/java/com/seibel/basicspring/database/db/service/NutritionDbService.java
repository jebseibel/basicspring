package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Nutrition;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.NutritionDb;
import com.seibel.basicspring.database.db.exceptions.DatabaseAccessException;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.db.mapper.NutritionMapper;
import com.seibel.basicspring.database.db.repository.NutritionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            NutritionDb entity = mapper.toDb(item);
            entity.setExtid(extid);
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            entity.setActive(ActiveEnum.ACTIVE);
            NutritionDb saved = repository.save(entity);
            log.info(createdMessage(extid));
            return mapper.toModel(saved);
        } catch (Exception e) {
            log.error(failedOperationMessage("create", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("create"), e);
        }
    }

    public Nutrition update(String extid, Nutrition item) {
        LocalDateTime now = LocalDateTime.now();

        try {
            NutritionDb existing = repository.findByExtid(extid)
                    .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));
            NutritionDb updated = mapper.toDb(item);
            updated.setId(existing.getId());
            updated.setExtid(existing.getExtid());
            updated.setCreatedAt(existing.getCreatedAt());
            updated.setUpdatedAt(now);
            NutritionDb saved = repository.save(updated);
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