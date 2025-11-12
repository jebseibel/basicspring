package com.seibel.cpss.database.db.service;

import com.seibel.cpss.common.domain.Mixture;
import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.MixtureDb;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.mapper.MixtureMapper;
import com.seibel.cpss.database.db.repository.MixtureRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class MixtureDbService extends BaseDbService {

    private final MixtureRepository repository;
    private final MixtureMapper mapper;

    public MixtureDbService(MixtureRepository repository, MixtureMapper mapper) {
        super("MixtureDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public Mixture create(@NonNull Mixture mixture) {
        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            mixture.setExtid(extid);
            mixture.setCreatedAt(now);
            mixture.setUpdatedAt(now);
            mixture.setActive(ActiveEnum.ACTIVE);

            MixtureDb mixtureDb = mapper.toDb(mixture);
            MixtureDb saved = repository.save(mixtureDb);

            log.info(createdMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            log.error(failedOperationMessage("create", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("create"), e);
        }
    }

    @Transactional
    public Mixture update(@NonNull String extid, @NonNull Mixture mixture) {
        MixtureDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(notFoundMessage(extid));
            return null;
        }

        try {
            record.setName(mixture.getName());
            record.setDescription(mixture.getDescription());
            record.setUpdatedAt(LocalDateTime.now());

            MixtureDb updated = repository.save(record);
            log.info(updatedMessage(extid));
            return mapper.toModel(updated);

        } catch (Exception e) {
            log.error(failedOperationMessage("update", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("update"), e);
        }
    }

    @Transactional
    public void delete(@NonNull String extid) {
        MixtureDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(notFoundMessage(extid));
            return;
        }

        try {
            record.setDeletedAt(LocalDateTime.now());
            record.setActive(ActiveEnum.INACTIVE);
            repository.save(record);
            log.info(deletedMessage(extid));

        } catch (Exception e) {
            log.error(failedOperationMessage("delete", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("delete"), e);
        }
    }

    public Mixture findByExtid(@NonNull String extid) {
        try {
            return repository.findByExtid(extid)
                    .map(mapper::toModel)
                    .orElse(null);

        } catch (Exception e) {
            log.error(failedOperationMessage("findByExtid", extid), e);
            throw new DatabaseFailureException(failedOperationMessage("findByExtid"), e);
        }
    }

    public List<Mixture> findByUserExtid(@NonNull String userExtid) {
        try {
            List<MixtureDb> mixtures = repository.findByUserExtidAndActive(userExtid, ActiveEnum.ACTIVE);
            return mapper.toModelList(mixtures);

        } catch (Exception e) {
            log.error("Failed to find mixtures for user: {}", userExtid, e);
            throw new DatabaseFailureException("Failed to find mixtures for user", e);
        }
    }

    public List<Mixture> findAll() {
        try {
            List<MixtureDb> all = repository.findAll();
            return mapper.toModelList(all);

        } catch (Exception e) {
            log.error("Failed to retrieve all mixtures", e);
            throw new DatabaseFailureException("Failed to retrieve all mixtures", e);
        }
    }
}
