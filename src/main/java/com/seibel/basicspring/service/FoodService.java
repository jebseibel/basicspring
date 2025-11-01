package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Food;
import com.seibel.basicspring.common.enums.ActiveEnum;

import com.seibel.basicspring.database.db.exception.DatabaseFailureException;
import com.seibel.basicspring.database.db.service.FoodDbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FoodService extends BaseService {

    private final FoodDbService dbService;

    public FoodService(FoodDbService dbService) {
        this.dbService = dbService;
        this.thisName = "Food";
    }

    public Food create(Food item) throws DatabaseFailureException {
        requireNonNull(item, "Food");
        log.info("Creating Food: {}", item);
        return dbService.create(item);
    }

    public Food update(String extid, Food item) throws DatabaseFailureException {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Food");
        log.info("Updating Food: {}", extid);
        return dbService.update(extid, item);
    }

    public boolean delete(String extid) throws DatabaseFailureException {
        requireNonBlank(extid, "extid");
        log.info("Deleting Food: {}", extid);
        return dbService.delete(extid);
    }

    public Food findByExtid(String extid) throws DatabaseFailureException {
        requireNonBlank(extid, "extid");
        return dbService.findByExtid(extid);
    }

    public List<Food> findAll() {
        return dbService.findAll();
    }

    public List<Food> findByActive(ActiveEnum active) {
        requireNonNull(active, "active");
        return dbService.findByActive(active);
    }
}
