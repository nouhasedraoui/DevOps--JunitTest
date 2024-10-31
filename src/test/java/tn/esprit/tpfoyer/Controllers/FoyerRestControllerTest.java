package tn.esprit.tpfoyer.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FoyerRestControllerTest {

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foyerRestController).build();
    }

    @Test
    void testGetFoyers() throws Exception {
        List<Foyer> foyers = new ArrayList<>();
        foyers.add(new Foyer(1L, "Foyer 1", 100, null, null));
        when(foyerService.retrieveAllFoyers()).thenReturn(foyers);

        mockMvc.perform(get("/foyer/retrieve-all-foyers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomFoyer").value("Foyer 1"));

        verify(foyerService, times(1)).retrieveAllFoyers();
    }

    @Test
    void testRetrieveFoyer() throws Exception {
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);

        mockMvc.perform(get("/foyer/retrieve-foyer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer").value("Foyer 1"));

        verify(foyerService, times(1)).retrieveFoyer(1L);
    }

    @Test
    void testAddFoyer() throws Exception {
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/add-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomFoyer\": \"Foyer 1\", \"capaciteFoyer\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer").value("Foyer 1"));

        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }

    @Test
    void testModifyFoyer() throws Exception {
        Foyer foyer = new Foyer(1L, "Foyer Modifié", 120, null, null);
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/foyer/modify-foyer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomFoyer\": \"Foyer Modifié\", \"capaciteFoyer\": 120}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomFoyer").value("Foyer Modifié"));

        verify(foyerService, times(1)).modifyFoyer(any(Foyer.class));
    }

    @Test
    void testRemoveFoyer() throws Exception {
        Long foyerId = 1L;

        mockMvc.perform(delete("/foyer/remove-foyer/1"))
                .andExpect(status().isOk());

        verify(foyerService, times(1)).removeFoyer(foyerId);
    }
}
