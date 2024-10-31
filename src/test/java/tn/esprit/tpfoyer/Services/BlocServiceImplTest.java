package tn.esprit.tpfoyer.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllBlocs() {
        List<Bloc> mockBlocs = new ArrayList<>();
        mockBlocs.add(new Bloc(1L, "Bloc A", 100L, null, null));
        mockBlocs.add(new Bloc(2L, "Bloc B", 80L, null, null));

        when(blocRepository.findAll()).thenReturn(mockBlocs);

        List<Bloc> blocs = blocService.retrieveAllBlocs();

        assertEquals(2, blocs.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveBlocById() {
        Bloc bloc = new Bloc(1L, "Bloc A", 100L, null, null);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc foundBloc = blocService.retrieveBloc(1L);

        assertNotNull(foundBloc);
        assertEquals("Bloc A", foundBloc.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddBloc() {
        Bloc bloc = new Bloc(1L, "Bloc A", 100L, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals("Bloc A", savedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testModifyBloc() {
        Bloc bloc = new Bloc(1L, "Bloc A", 100L, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc updatedBloc = blocService.modifyBloc(bloc);

        assertNotNull(updatedBloc);
        assertEquals("Bloc A", updatedBloc.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    public void testRemoveBloc() {
        Long blocId = 1L;

        blocService.removeBloc(blocId);

        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    public void testRetrieveBlocsSelonCapacite() {
        List<Bloc> mockBlocs = new ArrayList<>();
        mockBlocs.add(new Bloc(1L, "Bloc A", 100L, null, null));
        mockBlocs.add(new Bloc(2L, "Bloc B", 50L, null, null));

        when(blocRepository.findAll()).thenReturn(mockBlocs);

        List<Bloc> blocs = blocService.retrieveBlocsSelonCapacite(80L);

        assertEquals(1, blocs.size());
        assertEquals("Bloc A", blocs.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    public void testFindBlocsWithoutFoyer() {
        List<Bloc> mockBlocs = new ArrayList<>();
        mockBlocs.add(new Bloc(1L, "Bloc A", 100L, null, null));

        when(blocRepository.findAllByFoyerIsNull()).thenReturn(mockBlocs);

        List<Bloc> blocsWithoutFoyer = blocService.trouverBlocsSansFoyer();

        assertEquals(1, blocsWithoutFoyer.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    public void testFindBlocsByNomAndCapacite() {
        List<Bloc> mockBlocs = new ArrayList<>();
        mockBlocs.add(new Bloc(1L, "Bloc A", 100L, null, null));

        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 100L)).thenReturn(mockBlocs);

        List<Bloc> blocsByNomAndCapacite = blocService.trouverBlocsParNomEtCap("Bloc A", 100L);

        assertEquals(1, blocsByNomAndCapacite.size());
        assertEquals("Bloc A", blocsByNomAndCapacite.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc A", 100L);
    }
}
