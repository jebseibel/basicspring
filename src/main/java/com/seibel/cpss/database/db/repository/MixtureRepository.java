package com.seibel.cpss.database.db.repository;

import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.MixtureDb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MixtureRepository extends JpaRepository<MixtureDb, Long> {
    Optional<MixtureDb> findByExtid(String extid);
    List<MixtureDb> findByUserExtid(String userExtid);
    List<MixtureDb> findByUserExtidAndActive(String userExtid, ActiveEnum active);
    boolean existsByExtid(String extid);
}
