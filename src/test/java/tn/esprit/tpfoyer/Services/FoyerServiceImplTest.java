package tn.esprit.tpfoyer.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllFoyers() {
        // Préparation des données de test
        Foyer foyer1 = new Foyer(1L, "Foyer 1", 100, null, null);
        Foyer foyer2 = new Foyer(2L, "Foyer 2", 150, null, null);
        when(foyerRepository.findAll()).thenReturn(Arrays.asList(foyer1, foyer2));

        // Exécution de la méthode et vérification
        List<Foyer> foyers = foyerService.retrieveAllFoyers();
        assertEquals(2, foyers.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        // Préparation des données de test
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        // Exécution de la méthode et vérification
        Foyer foundFoyer = foyerService.retrieveFoyer(1L);
        assertEquals("Foyer 1", foundFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testAddFoyer() {
        // Préparation des données de test
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Exécution de la méthode et vérification
        Foyer addedFoyer = foyerService.addFoyer(foyer);
        assertEquals("Foyer 1", addedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer() {
        // Préparation des données de test
        Foyer foyer = new Foyer(1L, "Foyer Modifié", 120, null, null);
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Exécution de la méthode et vérification
        Foyer modifiedFoyer = foyerService.modifyFoyer(foyer);
        assertEquals("Foyer Modifié", modifiedFoyer.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRemoveFoyer() {
        Long foyerId = 1L;

        // Exécution de la méthode et vérification
        foyerService.removeFoyer(foyerId);
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}
