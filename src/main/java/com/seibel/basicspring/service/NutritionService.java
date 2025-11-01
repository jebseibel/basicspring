package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Nutrition;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.service.NutritionDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NutritionService extends BaseService {

    private final NutritionDbService dbService;

    public NutritionService(NutritionDbService dbService) {
        this.dbService = dbService;
        this.thisName = "Nutrition";
    }

    public Nutrition create(Nutrition item) {
        requireNonNull(item, thisName);
        log.info("Creating {}", thisName);
        return dbService.create(item);
    }

    public Nutrition update(String extid, Nutrition item) {
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

    public Nutrition findByExtid(String extid) {
        requireNonBlank(extid, "extid");
        return dbService.findByExtid(extid);
    }

    public List<Nutrition> findAll() {
        return dbService.findAll();
    }

    public List<Nutrition> findByActive(ActiveEnum activeEnum) {
        requireNonNull(activeEnum, "activeEnum");
        return dbService.findByActive(activeEnum);
    }
}
