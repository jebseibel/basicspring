package com.seibel.basicspring.database.database.db.service;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.entity.ProfileDb;
import com.seibel.basicspring.database.database.db.mapper.ProfileMapper;
import com.seibel.basicspring.database.database.db.repository.ProfileRepository;
import com.seibel.basicspring.database.database.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ProfileDbService extends BaseDbService {

    private final ProfileRepository repository;
    private final ProfileMapper mapper;

    public ProfileDbService(ProfileRepository repository, ProfileMapper mapper) {
        super("ProfileDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    public Profile create(@NonNull String nickname, @NonNull String fullname)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {

        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            ProfileDb record = new ProfileDb();
            record.setExtid(extid);
            record.setNickname(nickname);
            record.setFullname(fullname);
            record.setCreatedAt(now);
            record.setUpdatedAt(now);
            record.setActive(ActiveEnum.ACTIVE);

            ProfileDb saved = repository.save(record);
            log.info(getCreatedMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            handleException("create", extid, e);
            return null; // unreachable
        }
    }

    public Profile update(@NonNull String extid, String nickname, String fullname)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {

        ProfileDb record = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseRetrievalFailureException(getFoundFailureMessage(extid)));

        try {
            record.setNickname(nickname);
            record.setFullname(fullname);
            record.setUpdatedAt(LocalDateTime.now());

            ProfileDb saved = repository.save(record);
            log.info(getUpdatedMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            handleException("update", extid, e);
            return null;
        }
    }

    public boolean delete(@NonNull String extid)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {

        ProfileDb record = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseRetrievalFailureException(getFoundFailureMessage(extid)));

        try {
            record.setDeletedAt(LocalDateTime.now());
            record.setActive(ActiveEnum.INACTIVE);

            repository.save(record);
            log.info(getDeletedMessage(extid));
            return true;

        } catch (Exception e) {
            handleException("delete", extid, e);
            return false; // unreachable
        }
    }

    public Profile findByExtid(@NonNull String extid) throws DatabaseRetrievalFailureException {
        ProfileDb record = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseRetrievalFailureException(getFoundFailureMessage(extid)));
        log.info(getFoundMessage(extid));
        return mapper.toModel(record);
    }

    public List<Profile> findAll() {
        return findAndLog(repository.findAll(), "findAll");
    }

    public List<Profile> findByActive(@NonNull ActiveEnum activeEnum) {
        return findAndLog(repository.findByActive(activeEnum), String.format("active (%s)", activeEnum));
    }

    private List<Profile> findAndLog(List<ProfileDb> records, String type) {
        log.info(getFoundMessageByType(type, records.size()));
        return mapper.toModelList(records);
    }
}
