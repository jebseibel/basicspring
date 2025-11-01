package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.CompanyDb;
import com.seibel.basicspring.database.db.mapper.CompanyMapper;
import com.seibel.basicspring.database.db.repository.CompanyRepository;
import com.seibel.basicspring.database.db.exception.DatabaseFailureException;
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
public class CompanyDbService extends BaseDbService {

    private final CompanyRepository repository;
    private final CompanyMapper mapper;

    public CompanyDbService(CompanyRepository repository, CompanyMapper mapper) {
        super("CompanyDb");
        this.repository = repository;
        this.mapper = mapper;
    }

    public Company create(@NonNull String code, @NonNull String name, @NonNull String description) {
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
            log.error("Failed to create company: {}", extid, e);
            throw new DatabaseFailureException("Failed to create company", e);
        }
    }

    public Company update(@NonNull String extid, String code, String name, String description) {
        CompanyDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(getFoundFailureMessage(extid));
            return null;
        }
        try {
            record.setCode(code);
            record.setName(name);
            record.setDescription(description);
            record.setUpdatedAt(LocalDateTime.now());

            CompanyDb saved = repository.save(record);
            log.info(getUpdatedMessage(extid));
            return mapper.toModel(saved);

        } catch (Exception e) {
            log.error("Failed to update company: {}", extid, e);
            throw new DatabaseFailureException("Failed to update company", e);
        }
    }

    public boolean delete(@NonNull String extid) {
        CompanyDb record = repository.findByExtid(extid).orElse(null);
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
            log.error("Failed to delete company: {}", extid, e);
            throw new DatabaseFailureException("Failed to delete company", e);
        }
    }

    public Company findByExtid(@NonNull String extid) {
        CompanyDb record = repository.findByExtid(extid).orElse(null);
        if (record == null) {
            log.warn(getFoundFailureMessage(extid));
            return null;
        }
        log.info(getFoundMessage(extid));
        return mapper.toModel(record);
    }

    public List<Company> findAll() {
        return findAndLog(repository.findAll(), "findAll");
    }

    public Page<Company> findAll(Pageable pageable) {
        Page<CompanyDb> page = repository.findAll(pageable);
        log.info(getFoundMessageByType("findAll(pageable)", (int) page.getTotalElements()));
        return page.map(mapper::toModel);
    }

    public List<Company> findByActive(@NonNull ActiveEnum activeEnum) {
        // Use paged method with unpaged to keep backward compatibility
        Page<CompanyDb> page = repository.findByActive(activeEnum, Pageable.unpaged());
        log.info(getFoundMessageByType(String.format("active (%s)", activeEnum), (int) page.getTotalElements()));
        return mapper.toModelList(page.getContent());
    }

    public Page<Company> findByActive(@NonNull ActiveEnum activeEnum, Pageable pageable) {
        Page<CompanyDb> page = repository.findByActive(activeEnum, pageable);
        log.info(getFoundMessageByType(String.format("active (%s) pageable", activeEnum), (int) page.getTotalElements()));
        return page.map(mapper::toModel);
    }

    private List<Company> findAndLog(List<CompanyDb> records, String type) {
        log.info(getFoundMessageByType(type, records.size()));
        return mapper.toModelList(records);
    }
}