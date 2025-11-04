package com.seibel.cpss.database.db.repository;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.ServingDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServingRepository extends ListCrudRepository<ServingDb, Long> {

    Optional<ServingDb> findByExtid(String extid);
    List<ServingDb> findByActive(ActiveEnum active);
    boolean existsByExtid(String extid);
    Optional<ServingDb> findByCode(String code);
    Optional<ServingDb> findByName(String name);
}
