package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Serving;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.ServingDb;
import com.seibel.basicspring.database.db.exceptions.DatabaseAccessException;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.db.mapper.ServingMapper;
import com.seibel.basicspring.database.db.repository.ServingRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ServingDbService extends BaseDbService {

    private final ServingRepository repository;
    private final ServingMapper mapper;

    public ServingDbService(ServingRepository repository, ServingMapper mapper) {
        super("ServingDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    public Serving create(Serving item) {
        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            ServingDb entity = mapper.toDb(item);
            entity.setExtid(extid);
            entity.setActive(ActiveEnum.ACTIVE);
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            ServingDb saved = repository.save(entity);
            log.info(createdMessage(extid));
            return mapper.toModel(saved);
        } catch (Exception e) {
            log.error(failedOperationMessage("create", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("create"), e);
        }
    }

    public Serving update(String extid, Serving item) {
        LocalDateTime now = LocalDateTime.now();

        try {
            ServingDb existing = repository.findByExtid(extid)
                    .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));
            ServingDb updated = mapper.toDb(item);
            updated.setId(existing.getId());
            updated.setExtid(existing.getExtid());
            updated.setCreatedAt(existing.getCreatedAt());
            updated.setUpdatedAt(now);
            ServingDb saved = repository.save(updated);
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
            ServingDb entity = repository.findByExtid(extid)
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

    public Serving findByExtid(String extid) {
        try {
            return repository.findByExtid(extid)
                    .map(mapper::toModel)
                    .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));
        } catch (Exception e) {
            log.error(failedOperationMessage("find", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("find"), e);
        }
    }

    public List<Serving> findAll() {
        List<Serving> results = mapper.toModelList(repository.findAll());
        log.info(foundByActiveMessage("all", results.size()));
        return results;
    }

    public List<Serving> findByActive(ActiveEnum active) {
        List<Serving> results = mapper.toModelList(repository.findByActive(active));
        log.info(foundByActiveMessage(active.toString(), results.size()));
        return results;
    }
}