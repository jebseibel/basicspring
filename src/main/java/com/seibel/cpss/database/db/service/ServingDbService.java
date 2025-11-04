package com.seibel.cpss.database.db.service;

import com.seibel.cpss.common.domain.Serving;
import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.database.db.exceptions.DatabaseAccessException;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.mapper.ServingMapper;
import com.seibel.cpss.database.db.repository.ServingRepository;
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
        try {
            ServingDb existing = repository.findByExtid(extid)
                    .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));

            existing.setUpdatedAt(LocalDateTime.now());
            if (item.getName() != null) existing.setName(item.getName());
            if (item.getCategory() != null) existing.setCategory(item.getCategory());
            if (item.getSubcategory() != null) existing.setSubcategory(item.getSubcategory());
            if (item.getDescription() != null) existing.setDescription(item.getDescription());
            if (item.getNotes() != null) existing.setNotes(item.getNotes());
            if (item.getCup() != null) existing.setCup(item.getCup());
            if (item.getQuarter() != null) existing.setQuarter(item.getQuarter());
            if (item.getTablespoon() != null) existing.setTablespoon(item.getTablespoon());
            if (item.getTeaspoon() != null) existing.setTeaspoon(item.getTeaspoon());
            if (item.getGram() != null) existing.setGram(item.getGram());

            ServingDb saved = repository.save(existing);
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