package tn.esprit.tpfoyer.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.control.ChambreRestController;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ChambreRestControllerTest {

    @InjectMocks
    private ChambreRestController chambreRestController;

    @Mock
    private IChambreService chambreService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetChambres() {
        Chambre chambre1 = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        Chambre chambre2 = new Chambre(2L, 102, TypeChambre.DOUBLE, null, null);
        when(chambreService.retrieveAllChambres()).thenReturn(Arrays.asList(chambre1, chambre2));

        List<Chambre> result = chambreRestController.getChambres();

        assertThat(result.size()).isEqualTo(2);
        verify(chambreService, times(1)).retrieveAllChambres();
    }

    @Test
    public void testRetrieveChambre() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.retrieveChambre(anyLong())).thenReturn(chambre);

        var result = chambreRestController.retrieveChambre(1L);

        assertThat(result).isEqualTo(chambre);
        verify(chambreService, times(1)).retrieveChambre(1L);
    }

    @Test
    public void testAddChambre() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.addChambre(any())).thenReturn(chambre);

        var result = chambreRestController.addChambre(chambre);

        assertThat(result).isEqualTo(chambre);
        verify(chambreService, times(1)).addChambre(chambre);
    }

    @Test
    public void testRemoveChambre() {
        chambreRestController.removeChambre(1L);
        verify(chambreService, times(1)).removeChambre(1L);
    }

    @Test
    public void testModifyChambre() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.modifyChambre(any())).thenReturn(chambre);

        var result = chambreRestController.modifyChambre(chambre);

        assertThat(result).isEqualTo(chambre);
        verify(chambreService, times(1)).modifyChambre(chambre);
    }

    @Test
    public void testTrouverChambresSelonTyp() {
        Chambre chambre1 = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre1));

        var result = chambreRestController.trouverChSelonTC(TypeChambre.SIMPLE);

        assertThat(result.size()).isEqualTo(1);
        verify(chambreService, times(1)).recupererChambresSelonTyp(TypeChambre.SIMPLE);
    }

    @Test
    public void testTrouverChambreSelonEtudiant() {
        Chambre chambre = new Chambre(1L, 101, TypeChambre.SIMPLE, null, null);
        when(chambreService.trouverchambreSelonEtudiant(anyLong())).thenReturn(chambre);

        var result = chambreRestController.trouverChSelonEt(123456);

        assertThat(result).isEqualTo(chambre);
        verify(chambreService, times(1)).trouverchambreSelonEtudiant(123456);
    }
}
