package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Flavor;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.FlavorDb;
import com.seibel.basicspring.database.db.exceptions.DatabaseAccessException;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.db.mapper.FlavorMapper;
import com.seibel.basicspring.database.db.repository.FlavorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class FlavorDbService extends BaseDbService {

    private final FlavorRepository repository;
    private final FlavorMapper mapper;

    public FlavorDbService(FlavorRepository repository, FlavorMapper mapper) {
        super("FlavorDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    public Flavor create(Flavor item) throws DatabaseFailureException {
        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            FlavorDb entity = mapper.toDb(item);
            entity.setExtid(extid);
            entity.setCreatedAt(now);
            entity.setUpdatedAt(now);
            entity.setActive(ActiveEnum.ACTIVE);
            FlavorDb saved = repository.save(entity);
            log.info(createdMessage(extid));
            return mapper.toModel(saved);
        } catch (Exception e) {
            log.error(failedOperationMessage("create", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("create"), e);
        }
    }

    public Flavor update(String extid, Flavor item) throws DatabaseAccessException {
        FlavorDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));

        existing.setUpdatedAt(LocalDateTime.now());
        if (item.getName() != null) existing.setName(item.getName());
        if (item.getCategory() != null) existing.setCategory(item.getCategory());
        if (item.getSubcategory() != null) existing.setSubcategory(item.getSubcategory());
        if (item.getDescription() != null) existing.setDescription(item.getDescription());
        if (item.getNotes() != null) existing.setNotes(item.getNotes());
        if (item.getUsage() != null) existing.setUsage(item.getUsage());
        if (item.getCrunch() != null) existing.setCrunch(item.getCrunch());
        if (item.getPunch() != null) existing.setPunch(item.getPunch());
        if (item.getSweet() != null) existing.setSweet(item.getSweet());
        if (item.getSavory() != null) existing.setSavory(item.getSavory());

        FlavorDb saved = repository.save(existing);
        log.info(updatedMessage(extid));
        return mapper.toModel(saved);
    }

    public boolean delete(String extid) throws DatabaseAccessException {
        FlavorDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid)));

        existing.setDeletedAt(LocalDateTime.now());
        existing.setActive(ActiveEnum.INACTIVE);
        repository.save(existing);
        log.info(deletedMessage(extid));
        return true;
    }

    public Flavor findByExtid(String extid) throws DatabaseAccessException {
        return mapper.toModel(repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseAccessException(notFoundMessage(extid))));
    }

    public List<Flavor> findAll() {
        List<Flavor> results = mapper.toModelList(repository.findAll());
        log.info(foundByActiveMessage("all", results.size()));
        return results;
    }

    public List<Flavor> findByActive(ActiveEnum active) {
        List<Flavor> results = mapper.toModelList(repository.findByActive(active));
        log.info(foundByActiveMessage(active.toString(), results.size()));
        return results;
    }
}