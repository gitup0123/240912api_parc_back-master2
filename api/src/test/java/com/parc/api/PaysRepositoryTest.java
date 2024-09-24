package com.parc.api;

import com.parc.api.model.entity.Pays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaysRepositoryTest {
    @Mock

    private JpaRepository<Pays, Integer> paysRepository;

    @Test
    public void testGetAllPays(){
        List<Pays> paysList = List.of(new Pays(), new Pays());
        when(paysRepository.findAll()).thenReturn(paysList);
        List<Pays> result = paysRepository.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    @Test
    public void testFindById() {
        Pays pays = new Pays();
        pays.setId(1);
        when(paysRepository.findById(1)).thenReturn(java.util.Optional.of(pays));
        Pays result = paysRepository.findById(1).get();
        assertNotNull(result);
    }
    @Test
    public void testSave(){
        Pays pays = new Pays();
        pays.setId(1);
        when(paysRepository.save(any(Pays.class))).thenReturn(pays);
        Pays result = paysRepository.save(new Pays());
        assertNotNull(result);
        assertEquals(1, result.getId());
    }
    @Test
    public void testDeleteById(){
        doNothing().when(paysRepository).deleteById(1);
        paysRepository.deleteById(1);
        verify(paysRepository, times(1)).deleteById(1);
    }

}
