package com.seibel.cpss.database.db.mapper;

import com.seibel.cpss.common.domain.Food;
import com.seibel.cpss.database.db.entity.FoodDb;
import com.seibel.cpss.database.db.entity.FlavorDb;
import com.seibel.cpss.database.db.entity.NutritionDb;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.database.db.repository.FlavorRepository;
import com.seibel.cpss.database.db.repository.NutritionRepository;
import com.seibel.cpss.database.db.repository.ServingRepository;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodMapperTest {

    @Mock
    private FlavorRepository flavorRepository;
    @Mock
    private NutritionRepository nutritionRepository;
    @Mock
    private ServingRepository servingRepository;
    @Mock
    private FlavorMapper flavorMapper;
    @Mock
    private NutritionMapper nutritionMapper;
    @Mock
    private ServingMapper servingMapper;

    private FoodMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FoodMapper(flavorRepository, nutritionRepository, servingRepository,
                                flavorMapper, nutritionMapper, servingMapper);

        // Setup default mock behavior with lenient stubbings
        FlavorDb flavorDb = DomainBuilderDatabase.getFlavorDb();
        NutritionDb nutritionDb = DomainBuilderDatabase.getNutritionDb();
        ServingDb servingDb = DomainBuilderDatabase.getServingDb();

        org.mockito.Mockito.lenient().when(flavorRepository.findByCode(anyString())).thenReturn(Optional.of(flavorDb));
        org.mockito.Mockito.lenient().when(nutritionRepository.findByCode(anyString())).thenReturn(Optional.of(nutritionDb));
        org.mockito.Mockito.lenient().when(servingRepository.findByCode(anyString())).thenReturn(Optional.of(servingDb));

        org.mockito.Mockito.lenient().when(flavorMapper.toModel(flavorDb)).thenReturn(DomainBuilderDatabase.getFlavor(flavorDb));
        org.mockito.Mockito.lenient().when(nutritionMapper.toModel(nutritionDb)).thenReturn(DomainBuilderDatabase.getNutrition(nutritionDb));
        org.mockito.Mockito.lenient().when(servingMapper.toModel(servingDb)).thenReturn(DomainBuilderDatabase.getServing(servingDb));
    }

    @Test
    void toModel_shouldMapAllFields() {
        // Arrange
        FoodDb db = DomainBuilderDatabase.getFoodDb();

        // Act
        Food domain = mapper.toModel(db);

        // Assert
        assertNotNull(domain);
        assertEquals(db.getExtid(), domain.getExtid());
        assertEquals(db.getCode(), domain.getCode());
        assertEquals(db.getName(), domain.getName());
        assertEquals(db.getCategory(), domain.getCategory());
        assertEquals(db.getSubcategory(), domain.getSubcategory());
        assertEquals(db.getDescription(), domain.getDescription());
        assertNotNull(domain.getServing());
        assertNotNull(domain.getNutrition());
        assertNotNull(domain.getFlavor());
        assertEquals(db.getNotes(), domain.getNotes());
        assertEquals(db.getCreatedAt(), domain.getCreatedAt());
        assertEquals(db.getUpdatedAt(), domain.getUpdatedAt());
        assertEquals(db.getDeletedAt(), domain.getDeletedAt());
        assertEquals(db.getActive(), domain.getActive());
    }

    @Test
    void toDb_shouldMapAllFields() {
        // Arrange
        Food domain = DomainBuilderDatabase.getFood();

        // Act
        FoodDb db = mapper.toDb(domain);

        // Assert
        assertNotNull(db);
        assertEquals(domain.getExtid(), db.getExtid());
        assertEquals(domain.getCode(), db.getCode());
        assertEquals(domain.getName(), db.getName());
        assertEquals(domain.getCategory(), db.getCategory());
        assertEquals(domain.getSubcategory(), db.getSubcategory());
        assertEquals(domain.getDescription(), db.getDescription());
        assertNotNull(db.getServing());
        assertNotNull(db.getNutrition());
        assertNotNull(db.getFlavor());
        assertEquals(domain.getNotes(), db.getNotes());
        assertEquals(domain.getCreatedAt(), db.getCreatedAt());
        assertEquals(domain.getUpdatedAt(), db.getUpdatedAt());
        assertEquals(domain.getDeletedAt(), db.getDeletedAt());
        assertEquals(domain.getActive(), db.getActive());
    }

    @Test
    void toModelList_shouldMapAllItems() {
        // Arrange
        FoodDb db1 = DomainBuilderDatabase.getFoodDb();
        FoodDb db2 = DomainBuilderDatabase.getFoodDb();
        List<FoodDb> dbList = Arrays.asList(db1, db2);

        // Act
        List<Food> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(2, domainList.size());
        assertEquals(db1.getExtid(), domainList.get(0).getExtid());
        assertEquals(db2.getExtid(), domainList.get(1).getExtid());
    }

    @Test
    void toModelList_shouldHandleEmptyList() {
        // Arrange
        List<FoodDb> dbList = Arrays.asList();

        // Act
        List<Food> domainList = mapper.toModelList(dbList);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toModelList_shouldHandleNullList() {
        // Act
        List<Food> domainList = mapper.toModelList(null);

        // Assert
        assertNotNull(domainList);
        assertEquals(0, domainList.size());
    }

    @Test
    void toDbList_shouldMapAllItems() {
        // Arrange
        Food domain1 = DomainBuilderDatabase.getFood();
        Food domain2 = DomainBuilderDatabase.getFood();
        List<Food> domainList = Arrays.asList(domain1, domain2);

        // Act
        List<FoodDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(2, dbList.size());
        assertEquals(domain1.getExtid(), dbList.get(0).getExtid());
        assertEquals(domain2.getExtid(), dbList.get(1).getExtid());
    }

    @Test
    void toDbList_shouldHandleEmptyList() {
        // Arrange
        List<Food> domainList = Arrays.asList();

        // Act
        List<FoodDb> dbList = mapper.toDbList(domainList);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }

    @Test
    void toDbList_shouldHandleNullList() {
        // Act
        List<FoodDb> dbList = mapper.toDbList(null);

        // Assert
        assertNotNull(dbList);
        assertEquals(0, dbList.size());
    }
}
