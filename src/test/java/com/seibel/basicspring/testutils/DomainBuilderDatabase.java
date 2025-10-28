package com.seibel.basicspring.testutils;

import com.seibel.basicspring.common.domain.Company;
import com.seibel.basicspring.database.database.db.entity.CompanyDb;
import com.seibel.basicspring.database.database.db.mapper.CompanyMapper;

import java.util.UUID;

public class DomainBuilderDatabase extends DomainBuilderBase {


    // ///////////////////////////////////////////////////////////////////
    // Company
    public static Company getCompany() {
        CompanyDb item = getCompanyDb();
        return new CompanyMapper().toModel(item);
    }

    public static Company getCompany(CompanyDb item) {
        return new CompanyMapper().toModel(item);
    }

    public static CompanyDb getCompanyDb() {
        return getCompanyDb(null, null, null, null);
    }

    public static CompanyDb getCompanyDb(String code, String name) {
        return getCompanyDb(code, name, null, null);
    }

    public static CompanyDb getCompanyDb(String code, String name, String description, String extid) {
        CompanyDb item = new CompanyDb();
        item.setExtid(extid != null ? extid : UUID.randomUUID().toString());
        item.setCode(code != null ? code : getCodeRandom("CO_"));
        item.setName(name != null ? name : getNameRandom("Company_"));
        item.setDescription(description != null ? description : getDescriptionRandom("Company Description "));
        setBaseSyncFields(item);
        return item;
    }
}

