package com.seibel.basicspring.database.database.db.repository;

import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.entity.CompanyDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends ListCrudRepository<CompanyDb, Long> {
    Optional<CompanyDb> findByName(String name);
    Optional<CompanyDb> findByExtid(String extid);
    List<CompanyDb> findByActive(ActiveEnum active);
    boolean existsByExtid(String extid);
}
