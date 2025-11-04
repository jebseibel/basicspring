package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Serving;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ServingMapperTest {

    private ServingMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ServingMapper();
    }

    @Test
    void toModel_shouldMapAllFields() {
        // Arrange
        ServingDb db = DomainBuilderDatabase.getServingDb();

        // Act
        Serving domain = mapper.toModel(db);

        // Assert
        assertNotNull(domain);
        assertEquals(db.getExtid(), domain.getExtid());
        assertEquals(db.getCode(), domain.getCode());
        assertEquals(db.getName(), domain.getName());
        assertEquals(db.getCategory(), domain.getCategory());
        assertEquals(db.getSubcategory(), domain.getSubcategory());
        assertEquals(db.getDescription(), domain.getDescription());
        assertEquals(db.getCup(), domain.getCup());
        assertEquals(db.getQuarter(), domain.getQuarter());
        assertEquals(db.getTablespoon(), domain.getTablespoon());
        assertEquals(db.getTeaspoon(), domain.getTeaspoon());
        assertEquals(db.getGram(), domain.getGram());
        assertEquals(db.getCreatedAt(), domain.getCreatedAt());
        assertEquals(db.getUpdatedAt(), domain.getUpdatedAt());
        assertEquals(db.getDeletedAt(), domain.getDeletedAt());
        assertEquals(db.getActive(), domain.getActive());
    }

    @Test
    void toDb_shouldMapAllFields() {
        // Arrange
        Serving domain = DomainBuilderDatabase.getServing();

        // Act
        ServingDb db = mapper.toDb(domain);

        // Assert
        assertNotNull(db);
        assertEquals(domain.getExtid(), db.getExtid());
        assertEquals(domain.getCode(), db.getCode());
        assertEquals(domain.getName(), db.getName());
        assertEquals(domain.getCategory(), db.getCategory());
        assertEquals(domain.getSubcategory(), db.getSubcategory());
        assertEquals(domain.getDescription(), db.getDescription());
        assertEquals(domain.getCup(), db.getCup());
        assertEquals(domain.getQuarter(), db.getQuarter());
        assertEquals(domain.getTablespoon(), db.getTablespoon());
        assertEquals(domain.getTeaspoon(), db.getTeaspoon());
        assertEquals(domain.getGram(), db.getGram());
        assertEquals(domain.getCreatedAt(), db.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), db.getUpdatedAt());
        assertEquals(domain.getDeletedAt(), db.getDeletedAt());
        assertEquals(domain.getActive(), db.getActive());
    }

    @Test
    void toModelList_shouldMapAllItems() {
        // Arrange
        ServingDb db1 = DomainBuilderDatabase.getServingDb();
        ServingDb db2 = DomainBuilderDatabase.getServingDb();
        List<ServingDb> dbList = Arrays.asList(db1, db2);

        // Act
        List<Serving> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(2, domainList.size());
        assertEquals(db1.getExtid(), domainList.get(0).getExtid());
        assertEquals(db2.getExtid(), domainList.get(1).getExtid());
    }

    @Test
    void toModelList_shouldHandleEmptyList() {
        // Arrange
        List<ServingDb> dbList = Arrays.asList();

        // Act
        List<Serving> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toModelList_shouldHandleNullList() {
        // Act
        List<Serving> domainList = mapper.toModelList(null);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toDbList_shouldMapAllItems() {
        // Arrange
        Serving domain1 = DomainBuilderDatabase.getServing();
        Serving domain2 = DomainBuilderDatabase.getServing();
        List<Serving> domainList = Arrays.asList(domain1, domain2);

        // Act
        List<ServingDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(2, dbList.size());
        assertEquals(domain1.getExtid(), dbList.get(0).getExtid());
        assertEquals(domain2.getExtid(), dbList.get(1).getExtid());
    }

    @Test
    void toDbList_shouldHandleEmptyList() {
        // Arrange
        List<Serving> domainList = Arrays.asList();

        // Act
        List<ServingDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }

    @Test
    void toDbList_shouldHandleNullList() {
        // Act
        List<ServingDb> dbList = mapper.toDbList(null);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }
}
