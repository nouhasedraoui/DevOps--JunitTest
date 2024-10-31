package tn.esprit.tpfoyer.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        universite = new Universite(1L, "University A", "123 Street", null);
    }

    @Test
    void testRetrieveAllUniversites() {
        when(universiteRepository.findAll()).thenReturn(List.of(universite));

        List<Universite> universites = universiteService.retrieveAllUniversites();

        assertEquals(1, universites.size());
        assertEquals("University A", universites.get(0).getNomUniversite());
    }

    @Test
    void testRetrieveUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(1L);

        assertNotNull(result);
        assertEquals(1L, result.getIdUniversite());
        assertEquals("University A", result.getNomUniversite());
    }

    @Test
    void testAddUniversite() {
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);

        assertNotNull(result);
        assertEquals("University A", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testModifyUniversite() {
        universite.setNomUniversite("University B");
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.modifyUniversite(universite);

        assertEquals("University B", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRemoveUniversite() {
        doNothing().when(universiteRepository).deleteById(1L);

        universiteService.removeUniversite(1L);

        verify(universiteRepository, times(1)).deleteById(1L);
    }
}
