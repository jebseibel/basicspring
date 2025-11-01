package com.seibel.basicspring.database.db.service;


import com.seibel.basicspring.common.domain.Flavor;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.FlavorDb;
import com.seibel.basicspring.database.db.exception.DatabaseAccessException;
import com.seibel.basicspring.database.db.exception.DatabaseFailureException;
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
            return mapper.toModel(repository.save(entity));
        } catch (Exception e) {
            log.error("Failed to create flavor: {}", extid, e);
            throw new DatabaseFailureException("Failed to create flavor", e);
        }
    }

    public Flavor update(String extid, Flavor item) throws DatabaseAccessException {
        FlavorDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseAccessException("Flavor not found: " + extid));

        existing.setUpdatedAt(LocalDateTime.now());
        existing.setName(item.getName());
        existing.setCategory(item.getCategory());
        existing.setSubcategory(item.getSubcategory());
        existing.setDescription(item.getDescription());
        existing.setUsage(item.getUsage());

        return mapper.toModel(repository.save(existing));
    }

    public boolean delete(String extid) throws DatabaseAccessException {
        FlavorDb existing = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseAccessException("Flavor not found: " + extid));
        existing.setDeletedAt(LocalDateTime.now());
        existing.setActive(ActiveEnum.INACTIVE);
        repository.save(existing);
        return true;
    }

    public Flavor findByExtid(String extid) throws DatabaseAccessException {
        return mapper.toModel(repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseAccessException("Flavor not found: " + extid)));
    }

    public List<Flavor> findAll() {
        return mapper.toModelList(repository.findAll());
    }

    public List<Flavor> findByActive(ActiveEnum active) {
        return mapper.toModelList(repository.findByActive(active));
    }
}
