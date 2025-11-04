package com.seibel.cpss.database.db.service;

import com.seibel.cpss.common.domain.Serving;
import com.seibel.cpss.common.enums.ActiveEnum;
import com.seibel.cpss.database.db.entity.ServingDb;
import com.seibel.cpss.database.db.exceptions.DatabaseFailureException;
import com.seibel.cpss.database.db.mapper.ServingMapper;
import com.seibel.cpss.database.db.repository.ServingRepository;
import com.seibel.cpss.testutils.DomainBuilderDatabase;
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
class ServingDbServiceTest {

    @Mock
    private ServingRepository repository;

    @Mock
    private ServingMapper mapper;

    @InjectMocks
    private ServingDbService service;

    @Test
    void create_shouldGenerateUuidAndSetFields() {
        // Arrange
        Serving domain = DomainBuilderDatabase.getServing();
        ServingDb dbEntity = DomainBuilderDatabase.getServingDb();
        ServingDb savedDb = DomainBuilderDatabase.getServingDb();

        when(mapper.toDb(domain)).thenReturn(dbEntity);
        when(repository.save(any(ServingDb.class))).thenReturn(savedDb);
        when(mapper.toModel(savedDb)).thenReturn(domain);

        // Act
        Serving result = service.create(domain);

        // Assert
        assertNotNull(result);
        ArgumentCaptor<ServingDb> captor = ArgumentCaptor.forClass(ServingDb.class);
        verify(repository).save(captor.capture());

        ServingDb captured = captor.getValue();
        assertNotNull(captured.getExtid());
        assertEquals(ActiveEnum.ACTIVE, captured.getActive());
        verify(mapper).toModel(savedDb);
    }

    @Test
    void create_shouldThrowException_whenRepositoryFails() {
        // Arrange
        Serving domain = DomainBuilderDatabase.getServing();
        ServingDb dbEntity = DomainBuilderDatabase.getServingDb();

        when(mapper.toDb(domain)).thenReturn(dbEntity);
        when(repository.save(any(ServingDb.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        assertThrows(DatabaseFailureException.class, () -> {
            service.create(domain);
        });
    }

    @Test
    void update_shouldUpdateFieldsAndTimestamp() {
        // Arrange
        String extid = "existing-extid";
        Serving domain = DomainBuilderDatabase.getServing();
        ServingDb existingDb = DomainBuilderDatabase.getServingDb();
        existingDb.setExtid(extid);
        ServingDb updatedDb = DomainBuilderDatabase.getServingDb();

        when(repository.findByExtid(extid)).thenReturn(Optional.of(existingDb));
        when(repository.save(any(ServingDb.class))).thenReturn(updatedDb);
        when(mapper.toModel(updatedDb)).thenReturn(domain);

        // Act
        Serving result = service.update(extid, domain);

        // Assert
        assertNotNull(result);
        verify(repository).findByExtid(extid);
        verify(repository).save(any(ServingDb.class));
    }

    @Test
    void update_shouldReturnNull_whenNotFound() {
        // Arrange
        Serving domain = DomainBuilderDatabase.getServing();
        when(repository.findByExtid("nonexistent")).thenReturn(Optional.empty());

        // Act
        Serving result = service.update("nonexistent", domain);

        // Assert
        assertNull(result);
        verify(repository, never()).save(any());
    }

    @Test
    void delete_shouldSetDeletedAtAndInactive() {
        // Arrange
        String extid = "existing-extid";
        ServingDb existingDb = DomainBuilderDatabase.getServingDb();

        when(repository.findByExtid(extid)).thenReturn(Optional.of(existingDb));
        when(repository.save(any(ServingDb.class))).thenReturn(existingDb);

        // Act
        boolean result = service.delete(extid);

        // Assert
        assertTrue(result);
        ArgumentCaptor<ServingDb> captor = ArgumentCaptor.forClass(ServingDb.class);
        verify(repository).save(captor.capture());
        assertEquals(ActiveEnum.INACTIVE, captor.getValue().getActive());
    }

    @Test
    void findByExtid_shouldReturnServing_whenExists() {
        // Arrange
        String extid = "existing-extid";
        ServingDb db = DomainBuilderDatabase.getServingDb();
        Serving expectedDomain = DomainBuilderDatabase.getServing(db);

        when(repository.findByExtid(extid)).thenReturn(Optional.of(db));
        when(mapper.toModel(db)).thenReturn(expectedDomain);

        // Act
        Serving result = service.findByExtid(extid);

        // Assert
        assertNotNull(result);
        verify(repository).findByExtid(extid);
    }

    @Test
    void findAll_shouldReturnAllServings() {
        // Arrange
        ServingDb db1 = DomainBuilderDatabase.getServingDb();
        ServingDb db2 = DomainBuilderDatabase.getServingDb();
        List<ServingDb> dbList = Arrays.asList(db1, db2);

        when(repository.findAll()).thenReturn(dbList);
        when(mapper.toModelList(dbList)).thenReturn(Arrays.asList(
                DomainBuilderDatabase.getServing(db1),
                DomainBuilderDatabase.getServing(db2)
        ));

        // Act
        List<Serving> result = service.findAll();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void findByActive_shouldReturnFilteredServings() {
        // Arrange
        ServingDb db = DomainBuilderDatabase.getServingDb();
        db.setActive(ActiveEnum.ACTIVE);
        List<ServingDb> dbList = Arrays.asList(db);

        when(repository.findByActive(ActiveEnum.ACTIVE)).thenReturn(dbList);
        when(mapper.toModelList(dbList)).thenReturn(Arrays.asList(DomainBuilderDatabase.getServing(db)));

        // Act
        List<Serving> result = service.findByActive(ActiveEnum.ACTIVE);

        // Assert
        assertEquals(1, result.size());
    }
}
