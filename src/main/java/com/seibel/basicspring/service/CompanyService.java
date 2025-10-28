package com.seibel.basicspring.service;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.database.db.service.CompanyDbService;
import com.seibel.basicspring.database.database.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyService extends BaseService {

    private final CompanyDbService companyDbService;

    public CompanyService(CompanyDbService companyDbService) {
        this.companyDbService = companyDbService;
        this.thisName = "Company";
    }

    public Company create(Company item) throws DatabaseFailureException, DatabaseRetrievalFailureException {
        requireNonNull(item, "Company");
        log.info("create(): {}", item);
        return companyDbService.create(item.getCode(), item.getName(), item.getDescription());
    }

    public Company update(String extid, Company item)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {
        requireNonBlank(extid, "extid");
        requireNonNull(item, "Company");

        log.info("update(): extid={}, {}", extid,  item);

        return companyDbService.update(extid, item.getCode(), item.getName(), item.getDescription());
    }

    public boolean delete(String extid)
            throws DatabaseFailureException, DatabaseRetrievalFailureException {
        requireNonBlank(extid, "extid");
        log.info("delete(): extid={}", extid);
        return companyDbService.delete(extid);
    }

    public Company findByExtid(String extid) throws DatabaseRetrievalFailureException {
        requireNonBlank(extid, "extid");
        log.info("findByExtid(): extid={}", extid);
        return companyDbService.findByExtid(extid);
    }

    public List<Company> findAll() throws DatabaseRetrievalFailureException {
        log.info("findAll()");
        return companyDbService.findAll();
    }

    public List<Company> findByActive(ActiveEnum activeEnum) throws DatabaseRetrievalFailureException {
        requireNonNull(activeEnum, "activeEnum");
        log.info("findByActive(): activeEnum={}", activeEnum);
        return companyDbService.findByActive(activeEnum);
    }
}
