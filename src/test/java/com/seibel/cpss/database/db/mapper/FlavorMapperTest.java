package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Flavor;
import com.seibel.cpss.database.db.entity.FlavorDb;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FlavorMapperTest {

    private FlavorMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FlavorMapper();
    }

    @Test
    void toModel_shouldMapAllFields() {
        // Arrange
        FlavorDb db = DomainBuilderDatabase.getFlavorDb();

        // Act
        Flavor domain = mapper.toModel(db);

        // Assert
        assertNotNull(domain);
        assertEquals(db.getExtid(), domain.getExtid());
        assertEquals(db.getCode(), domain.getCode());
        assertEquals(db.getName(), domain.getName());
        assertEquals(db.getCategory(), domain.getCategory());
        assertEquals(db.getSubcategory(), domain.getSubcategory());
        assertEquals(db.getDescription(), domain.getDescription());
        assertEquals(db.getUsage(), domain.getUsage());
        assertEquals(db.getCreatedAt(), domain.getCreatedAt());
        assertEquals(db.getUpdatedAt(), domain.getUpdatedAt());
        assertEquals(db.getDeletedAt(), domain.getDeletedAt());
        assertEquals(db.getActive(), domain.getActive());
    }

    @Test
    void toDb_shouldMapAllFields() {
        // Arrange
        Flavor domain = DomainBuilderDatabase.getFlavor();

        // Act
        FlavorDb db = mapper.toDb(domain);

        // Assert
        assertNotNull(db);
        assertEquals(domain.getExtid(), db.getExtid());
        assertEquals(domain.getCode(), db.getCode());
        assertEquals(domain.getName(), db.getName());
        assertEquals(domain.getCategory(), db.getCategory());
        assertEquals(domain.getSubcategory(), db.getSubcategory());
        assertEquals(domain.getDescription(), db.getDescription());
        assertEquals(domain.getUsage(), db.getUsage());
        assertEquals(domain.getCreatedAt(), db.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), db.getUpdatedAt());
        assertEquals(domain.getDeletedAt(), db.getDeletedAt());
        assertEquals(domain.getActive(), db.getActive());
    }

    @Test
    void toModelList_shouldMapAllItems() {
        // Arrange
        FlavorDb db1 = DomainBuilderDatabase.getFlavorDb();
        FlavorDb db2 = DomainBuilderDatabase.getFlavorDb();
        List<FlavorDb> dbList = Arrays.asList(db1, db2);

        // Act
        List<Flavor> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(2, domainList.size());
        assertEquals(db1.getExtid(), domainList.get(0).getExtid());
        assertEquals(db2.getExtid(), domainList.get(1).getExtid());
    }

    @Test
    void toModelList_shouldHandleEmptyList() {
        // Arrange
        List<FlavorDb> dbList = Arrays.asList();

        // Act
        List<Flavor> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toModelList_shouldHandleNullList() {
        // Act
        List<Flavor> domainList = mapper.toModelList(null);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toDbList_shouldMapAllItems() {
        // Arrange
        Flavor domain1 = DomainBuilderDatabase.getFlavor();
        Flavor domain2 = DomainBuilderDatabase.getFlavor();
        List<Flavor> domainList = Arrays.asList(domain1, domain2);

        // Act
        List<FlavorDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(2, dbList.size());
        assertEquals(domain1.getExtid(), dbList.get(0).getExtid());
        assertEquals(domain2.getExtid(), dbList.get(1).getExtid());
    }

    @Test
    void toDbList_shouldHandleEmptyList() {
        // Arrange
        List<Flavor> domainList = Arrays.asList();

        // Act
        List<FlavorDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }

    @Test
    void toDbList_shouldHandleNullList() {
        // Act
        List<FlavorDb> dbList = mapper.toDbList(null);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }
}
