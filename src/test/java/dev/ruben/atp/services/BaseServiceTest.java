package dev.ruben.atp.services;

import dev.ruben.atp.services.BaseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BaseServiceTest {

    @Mock
    private JpaRepository<Object, Integer> repository;

    private BaseService<Object, Integer, JpaRepository<Object, Integer>> baseService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        baseService = new BaseService<Object, Integer, JpaRepository<Object, Integer>>() {

            protected JpaRepository<Object, Integer> getRepository() {
                return repository;
            }
        };
    }

    @Test
    @DisplayName("Should return saved object when save is called")
    public void shouldReturnSavedObjectWhenSaveCalled() {
        Object object = new Object();
        when(repository.save(object)).thenReturn(object);

        Object result = repository.save(object);

        assertEquals(object, result);
    }

    @Test
    @DisplayName("Should return object when findById is called with existing id")
    public void shouldReturnObjectWhenFindByIdCalledWithExistingId() {
        Object object = new Object();
        when(repository.findById(1)).thenReturn(Optional.of(object));

        Optional<Object> result = repository.findById(1);

        assertEquals(object, result.orElse(null));
    }

    @Test
    @DisplayName("Should return all objects when findAll is called")
    public void shouldReturnAllObjectsWhenFindAllCalled() {
        List<Object> objects = Arrays.asList(new Object(), new Object());
        when(repository.findAll()).thenReturn(objects);

        List<Object> result = repository.findAll();

        assertEquals(objects, result);
    }

    @Test
    @DisplayName("Should return paged objects when findAll with pageable is called")
    public void shouldReturnPagedObjectsWhenFindAllWithPageableCalled() {
        Page<Object> page = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(repository.findAll(pageable)).thenReturn(page);

        Page<Object> result = repository.findAll(pageable);

        assertEquals(page, result);
    }

    @Test
    @DisplayName("Should delete object when delete is called")
    public void shouldDeleteObjectWhenDeleteCalled() {
        Object object = new Object();

        repository.delete(object);

        verify(repository).delete(object);
    }

    @Test
    @DisplayName("Should delete object by id when deleteById is called")
    public void shouldDeleteObjectByIdWhenDeleteByIdCalled() {
        repository.deleteById(1);

        verify(repository).deleteById(1);
    }
}