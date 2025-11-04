package com.seibel.basicspring.database.db.service;

import com.seibel.basicspring.common.domain.Flavor;
import com.seibel.basicspring.common.enums.ActiveEnum;
import com.seibel.basicspring.database.db.entity.FlavorDb;
import com.seibel.basicspring.database.db.exceptions.DatabaseFailureException;
import com.seibel.basicspring.database.db.mapper.FlavorMapper;
import com.seibel.basicspring.database.db.repository.FlavorRepository;
import com.seibel.basicspring.testutils.DomainBuilderDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlavorDbServiceTest {

    @Mock
    private FlavorRepository repository;

    @Mock
    private FlavorMapper mapper;

    @InjectMocks
    private FlavorDbService service;

    @Test
    void create_shouldGenerateUuidAndSetFields() {
        // Arrange
        Flavor domain = DomainBuilderDatabase.getFlavor();
        FlavorDb dbEntity = DomainBuilderDatabase.getFlavorDb();
        FlavorDb savedDb = DomainBuilderDatabase.getFlavorDb();

        when(mapper.toDb(domain)).thenReturn(dbEntity);
        when(repository.save(any(FlavorDb.class))).thenReturn(savedDb);
        when(mapper.toModel(savedDb)).thenReturn(domain);

        // Act
        Flavor result = service.create(domain);

        // Assert
        assertNotNull(result);
        ArgumentCaptor<FlavorDb> captor = ArgumentCaptor.forClass(FlavorDb.class);
        verify(repository).save(captor.capture());
        assertEquals(ActiveEnum.ACTIVE, captor.getValue().getActive());
    }

    @Test
    void create_shouldThrowException_whenRepositoryFails() {
        // Arrange
        Flavor domain = DomainBuilderDatabase.getFlavor();
        FlavorDb dbEntity = DomainBuilderDatabase.getFlavorDb();

        when(mapper.toDb(domain)).thenReturn(dbEntity);
        when(repository.save(any(FlavorDb.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(DatabaseFailureException.class, () -> {
            service.create(domain);
        });
    }

    @Test
    void update_shouldUpdateFieldsAndTimestamp() {
        // Arrange
        String extid = "existing-extid";
        Flavor domain = DomainBuilderDatabase.getFlavor();
        FlavorDb existingDb = DomainBuilderDatabase.getFlavorDb();
        existingDb.setExtid(extid);

        when(repository.findByExtid(extid)).thenReturn(Optional.of(existingDb));
        when(repository.save(any(FlavorDb.class))).thenReturn(existingDb);
        when(mapper.toModel(existingDb)).thenReturn(domain);

        // Act
        Flavor result = service.update(extid, domain);

        // Assert
        assertNotNull(result);
        verify(repository).save(any(FlavorDb.class));
    }

    @Test
    void update_shouldThrowException_whenNotFound() {
        // Arrange
        Flavor domain = DomainBuilderDatabase.getFlavor();
        when(repository.findByExtid("nonexistent")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DatabaseFailureException.class, () -> {
            service.update("nonexistent", domain);
        });
        verify(repository, never()).save(any());
    }

    @Test
    void delete_shouldSetDeletedAtAndInactive() {
        // Arrange
        String extid = "existing-extid";
        FlavorDb existingDb = DomainBuilderDatabase.getFlavorDb();

        when(repository.findByExtid(extid)).thenReturn(Optional.of(existingDb));
        when(repository.save(any(FlavorDb.class))).thenReturn(existingDb);

        // Act
        boolean result = service.delete(extid);

        // Assert
        assertTrue(result);
    }

    @Test
    void findByExtid_shouldReturnFlavor_whenExists() {
        // Arrange
        String extid = "existing-extid";
        FlavorDb db = DomainBuilderDatabase.getFlavorDb();
        Flavor expectedDomain = DomainBuilderDatabase.getFlavor(db);

        when(repository.findByExtid(extid)).thenReturn(Optional.of(db));
        when(mapper.toModel(db)).thenReturn(expectedDomain);

        // Act
        Flavor result = service.findByExtid(extid);

        // Assert
        assertNotNull(result);
    }

    @Test
    void findAll_shouldReturnAllFlavors() {
        // Arrange
        FlavorDb db1 = DomainBuilderDatabase.getFlavorDb();
        FlavorDb db2 = DomainBuilderDatabase.getFlavorDb();
        List<FlavorDb> dbList = Arrays.asList(db1, db2);

        when(repository.findAll()).thenReturn(dbList);
        when(mapper.toModelList(dbList)).thenReturn(Arrays.asList(
                DomainBuilderDatabase.getFlavor(db1),
                DomainBuilderDatabase.getFlavor(db2)
        ));

        // Act
        List<Flavor> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findByActive_shouldReturnFilteredFlavors() {
        // Arrange
        FlavorDb db = DomainBuilderDatabase.getFlavorDb();
        db.setActive(ActiveEnum.ACTIVE);
        List<FlavorDb> dbList = Arrays.asList(db);

        when(repository.findByActive(ActiveEnum.ACTIVE)).thenReturn(dbList);
        when(mapper.toModelList(dbList)).thenReturn(Arrays.asList(DomainBuilderDatabase.getFlavor(db)));

        // Act
        List<Flavor> result = service.findByActive(ActiveEnum.ACTIVE);

        // Assert
        assertEquals(1, result.size());
    }
}
