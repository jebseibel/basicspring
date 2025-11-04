package com.seibel.cpss.service;


import com.seibel.cpss.common.domain.Flavor;
import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.exceptions.DatabaseAccessException;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.service.FlavorDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FlavorService extends BaseService {

    private final FlavorDbService dbService;

    public FlavorService(FlavorDbService dbService) {
        super(Flavor.class.getSimpleName());
        this.dbService = dbService;
    }

    public Flavor create(Flavor item) throws DatabaseFailureException {
        requireNonNull(item, "Flavor");
        log.info("Creating Flavor: {}", item);
        return dbService.create(item);
    }

    public Flavor update(String extid, Flavor item) throws DatabaseAccessException {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Flavor");
        log.info("Updating Flavor: {}", extid);
        return dbService.update(extid, item);
    }

    public boolean delete(String extid) throws DatabaseAccessException {
        requireNonBlank(extid, "extid");
        log.info("Deleting Flavor: {}", extid);
        return dbService.delete(extid);
    }

    public Flavor findByExtid(String extid) throws DatabaseAccessException {
        requireNonBlank(extid, "extid");
        return dbService.findByExtid(extid);
    }

    public List<Flavor> findAll() {
        return dbService.findAll();
    }

    public List<Flavor> findByActive(ActiveEnum active) {
        requireNonNull(active, "active");
        return dbService.findByActive(active);
    }
}
