package com.seibel.basicspring.database.database.db.repository;

import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.entity.ProfileDb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileDb, Long> {
    Optional<ProfileDb> findByExtid(String extid);
    Page<ProfileDb> findByActive(ActiveEnum active, Pageable pageable);
    boolean existsByExtid(String extid);
}
