package com.seibel.cpss.database.db.repository;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.SaladDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaladRepository extends JpaRepository<SaladDb, Long> {
    Optional<SaladDb> findByExtid(String extid);
    List<SaladDb> findByUserExtid(String userExtid);
    List<SaladDb> findByUserExtidAndActive(String userExtid, ActiveEnum active);
    boolean existsByExtid(String extid);
}
