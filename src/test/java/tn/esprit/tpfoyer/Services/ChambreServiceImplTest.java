package tn.esprit.tpfoyer.Services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ChambreServiceImplTest {

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @Mock
    private ChambreRepository chambreRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllChambres() {
        Chambre chambre1 = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        Chambre chambre2 = new Chambre(2L, 102, TypeChambre.DOUBLE, null, null);
        when(chambreRepository.findAll()).thenReturn(Arrays.asList(chambre1, chambre2));

        var result = chambreService.retrieveAllChambres();

        assertThat(result.size()).isEqualTo(2);
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveChambre() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findById(anyLong())).thenReturn(Optional.of(chambre));

        var result = chambreService.retrieveChambre(1L);

        assertThat(result).isEqualTo(chambre);
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddChambre() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(any())).thenReturn(chambre);

        var result = chambreService.addChambre(chambre);

        assertThat(result).isEqualTo(chambre);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testModifyChambre() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.save(any())).thenReturn(chambre);

        var result = chambreService.modifyChambre(chambre);

        assertThat(result).isEqualTo(chambre);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testRemoveChambre() {
        chambreService.removeChambre(1L);
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRecupererChambresSelonTyp() {
        Chambre chambre1 = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreRepository.findAllByTypeC(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre1));

        var result = chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE);

        assertThat(result.size()).isEqualTo(1);
        verify(chambreRepository, times(1)).findAllByTypeC(TypeChambre.SIMPLE);
    }
}
