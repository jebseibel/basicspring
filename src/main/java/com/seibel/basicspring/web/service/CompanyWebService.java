package com.seibel.basicspring.web.service;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.database.database.db.mapper.CompanyMapper;
import com.seibel.basicspring.database.database.exceptions.DatabaseRetrievalFailureException;
import com.seibel.basicspring.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is primarily to deal with business logic.
 */
@Slf4j
@Service
public class CompanyWebService {

    CompanyService companyService;
    CompanyMapper accountMapper;

    public CompanyWebService(CompanyService companyService, CompanyMapper accountMapper) {
        this.companyService = companyService;
        this.accountMapper = accountMapper;
    }

    public Company create(Company item) {
        log.info("create(): {}", item);
        try {
            return companyService.create(item);
        } catch (Exception e) {
            log.error("create() failed: {}", e.getMessage(), e);
            return null;
        }
    }

    public Company update(String extid, Company item) {
        log.info("update(): extid={}, origin={}, item={}", extid, item);
        try {
            return companyService.update(extid, item);
        } catch (Exception e) {
            log.error("update() failed for extid={}: {}", extid, e.getMessage(), e);
            return null;
        }
    }

    public Company getByExtid(String extid) {
        log.info("getByExtid(): extid={}", extid);
        try {
            return companyService.findByExtid(extid);
        } catch (Exception e) {
            log.error("getByExtid() failed for extid={}: {}", extid, e.getMessage(), e);
            return null;
        }
    }

    public boolean delete(String extid) {
        log.info("delete(): extid={}", extid);
        try {
            return companyService.delete(extid);
        } catch (Exception e) {
            log.error("delete() failed for extid={}: {}", extid, e.getMessage(), e);
            return false;
        }
    }

    public List<Company> getAll() throws DatabaseRetrievalFailureException {
        log.info("getAll()");
        List<Company> result = companyService.findAll();
        log.info("getAll() returned [{}] items", result.size());
        return result;
    }
}
