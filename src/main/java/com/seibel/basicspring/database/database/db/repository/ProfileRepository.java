package com.seibel.basicspring.database.database.db.repository;

import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.entity.ProfileDb;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends ListCrudRepository<ProfileDb, Long> {
    Optional<ProfileDb> findByExtid(String extid);
    List<ProfileDb> findByActive(ActiveEnum active);
    boolean existsByExtid(String extid);
}
