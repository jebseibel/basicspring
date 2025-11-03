package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Serving;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.service.ServingDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ServingService extends BaseService {

    private final ServingDbService dbService;

    public ServingService(ServingDbService dbService) {
        super(Serving.class.getSimpleName());
        this.dbService = dbService;
    }

    public Serving create(Serving item) {
        requireNonNull(item, thisName);
        log.info("Creating {}", thisName);
        return dbService.create(item);
    }

    public Serving update(String extid, Serving item) {
        requireNonBlank(extid, "extid");
        requireNonNull(item, thisName);
        log.info("Updating {} {}", thisName, extid);
        return dbService.update(extid, item);
    }

    public boolean delete(String extid) {
        requireNonBlank(extid, "extid");
        log.info("Deleting {} {}", thisName, extid);
        return dbService.delete(extid);
    }

    public Serving findByExtid(String extid) {
        requireNonBlank(extid, "extid");
        return dbService.findByExtid(extid);
    }

    public List<Serving> findAll() {
        return dbService.findAll();
    }

    public List<Serving> findByActive(ActiveEnum activeEnum) {
        requireNonNull(activeEnum, "activeEnum");
        return dbService.findByActive(activeEnum);
    }
}
