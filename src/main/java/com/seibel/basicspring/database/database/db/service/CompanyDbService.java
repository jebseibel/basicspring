package com.seibel.basicspring.database.database.db.service;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.entity.CompanyDb;
import com.seibel.basicspring.database.database.db.mapper.CompanyMapper;
import com.seibel.basicspring.database.database.db.repository.CompanyRepository;
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
public class CompanyDbService extends BaseDbService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public CompanyDbService(CompanyRepository repository, CompanyMapper mapper) {
        super("CompanyDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    public Company create(@NonNull String code, @NonNull String name, @NonNull String description)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {

        String extid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();

        try {
            CompanyDb record = new CompanyDb();
            record.setExtid(extid);
            record.setCode(code);
            record.setName(name);
            record.setDescription(description);
            record.setCreatedAt(now);
            record.setUpdatedAt(now);
            record.setActive(ActiveEnum.ACTIVE);

            CompanyDb saved = repository.save(record);
            log.info(getCreatedMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            handleException("create", extid, e);
            return null; // unreachable
        }
    }

    public Company update(@NonNull String extid, String code, String name, String description)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {

        CompanyDb record = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseRetrievalFailureException(getFoundFailureMessage(extid)));

        try {
            record.setCode(code);
            record.setName(name);
            record.setDescription(description);
            record.setUpdatedAt(LocalDateTime.now());

            CompanyDb saved = repository.save(record);
            log.info(getUpdatedMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            handleException("update", extid, e);
            return null;
        }
    }

    public boolean delete(@NonNull String extid)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {

        CompanyDb record = repository.findByExtid(extid)
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

    public Company findByExtid(@NonNull String extid) throws DatabaseRetrievalFailureException {
        CompanyDb record = repository.findByExtid(extid)
                .orElseThrow(() -> new DatabaseRetrievalFailureException(getFoundFailureMessage(extid)));
        log.info(getFoundMessage(extid));
        return mapper.toModel(record);
    }

    public List<Company> findAll() {
        return findAndLog(repository.findAll(), "findAll");
    }

    public List<Company> findByActive(@NonNull ActiveEnum activeEnum) {
        return findAndLog(repository.findByActive(activeEnum),
                String.format("active (%s)", activeEnum));
    }

    private List<Company> findAndLog(List<CompanyDb> records, String type) {
        log.info(getFoundMessageByType(type, records.size()));
        return mapper.toModelList(records);
    }
}
