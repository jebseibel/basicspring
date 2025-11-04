package com.seibel.cpss.database.db.repository;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.FlavorDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlavorRepository extends ListCrudRepository<FlavorDb, Long> {
    Optional<FlavorDb> findByExtid(String extid);
    List<FlavorDb> findByActive(ActiveEnum active);
    boolean existsByExtid(String extid);
    Optional<FlavorDb> findByCode(String code);
    Optional<FlavorDb> findByName(String name);
}

