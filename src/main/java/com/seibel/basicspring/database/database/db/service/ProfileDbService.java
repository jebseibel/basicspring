package com.seibel.basicspring.database.database.db.service;

import com.seibel.basicspring.common.domain.Profile;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.entity.ProfileDb;
import com.seibel.basicspring.database.database.db.mapper.ProfileMapper;
import com.seibel.basicspring.database.database.db.repository.ProfileRepository;
import com.seibel.basicspring.database.database.exception.DatabaseException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Profile create(@NonNull String nickname, @NonNull String fullname) {
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
            log.error("Failed to create profile: {}", extid, e);
            throw new DatabaseException("Failed to create profile", e);
        }
    }

    public Profile update(@NonNull String extid, String nickname, String fullname) {
        ProfileDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(getFoundFailureMessage(extid));
            return null;
        }
        try {
            record.setNickname(nickname);
            record.setFullname(fullname);
            record.setUpdatedAt(LocalDateTime.now());

            ProfileDb saved = repository.save(record);
            log.info(getUpdatedMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            log.error("Failed to update profile: {}", extid, e);
            throw new DatabaseException("Failed to update profile", e);
        }
    }

    public boolean delete(@NonNull String extid) {
        ProfileDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(getFoundFailureMessage(extid));
            return false;
        }
        try {
            record.setDeletedAt(LocalDateTime.now());
            record.setActive(ActiveEnum.INACTIVE);

            repository.save(record);
            log.info(getDeletedMessage(extid));
            return true;

        } catch (Exception e) {
            log.error("Failed to delete profile: {}", extid, e);
            throw new DatabaseException("Failed to delete profile", e);
        }
    }

    public Profile findByExtid(@NonNull String extid) {
        ProfileDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(getFoundFailureMessage(extid));
            return null;
        }
        log.info(getFoundMessage(extid));
        return mapper.toModel(record);
    }

    public List<Profile> findAll() {
        return findAndLog(repository.findAll(), "findAll");
    }

    public Page<Profile> findAll(Pageable pageable) {
        Page<ProfileDb> page = repository.findAll(pageable);
        log.info(getFoundMessageByType("findAll(pageable)", (int) page.getTotalElements()));
        return page.map(mapper::toModel);
    }

    public List<Profile> findByActive(@NonNull ActiveEnum activeEnum) {
        Page<ProfileDb> page = repository.findByActive(activeEnum, Pageable.unpaged());
        log.info(getFoundMessageByType(String.format("active (%s)", activeEnum), (int) page.getTotalElements()));
        return mapper.toModelList(page.getContent());
    }

    public Page<Profile> findByActive(@NonNull ActiveEnum activeEnum, Pageable pageable) {
        Page<ProfileDb> page = repository.findByActive(activeEnum, pageable);
        log.info(getFoundMessageByType(String.format("active (%s) pageable", activeEnum), (int) page.getTotalElements()));
        return page.map(mapper::toModel);
    }

    private List<Profile> findAndLog(List<ProfileDb> records, String type) {
        log.info(getFoundMessageByType(type, records.size()));
        return mapper.toModelList(records);
    }
}