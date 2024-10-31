package tn.esprit.tpfoyer.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.BlocRestController;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer; // Import the Foyer entity
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlocRestController.class)
class BlocRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IBlocService blocService;

    private Bloc bloc;
    private Foyer foyer; // Declare a Foyer instance

    @BeforeEach
    void setUp() {
        // Create a mock Foyer instance if needed
        foyer = mock(Foyer.class);

        // Initialize the Bloc without the Foyer (using the default constructor)
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);
        bloc.setFoyer(foyer); // Optional, only if you need it for the test
    }

    @Test
    void testGetBlocs() throws Exception {
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(bloc);
        when(blocService.retrieveAllBlocs()).thenReturn(blocs);

        mockMvc.perform(get("/bloc/retrieve-all-blocs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$[0].capaciteBloc").value(100));
    }

    @Test
    void testRetrieveBloc() throws Exception {
        when(blocService.retrieveBloc(1L)).thenReturn(bloc);

        mockMvc.perform(get("/bloc/retrieve-bloc/{bloc-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$.capaciteBloc").value(100));
    }

    @Test
    void testAddBloc() throws Exception {
        when(blocService.addBloc(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(post("/bloc/add-bloc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$.capaciteBloc").value(100));
    }

    @Test
    void testRemoveBloc() throws Exception {
        doNothing().when(blocService).removeBloc(1L);

        mockMvc.perform(delete("/bloc/remove-bloc/{bloc-id}", 1L))
                .andExpect(status().isOk());

        verify(blocService, times(1)).removeBloc(1L);
    }

    @Test
    void testModifyBloc() throws Exception {
        bloc.setNomBloc("Updated Bloc");
        when(blocService.modifyBloc(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(put("/bloc/modify-bloc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomBloc").value("Updated Bloc"));
    }

    @Test
    void testGetBlocsWithoutFoyer() throws Exception {
        List<Bloc> blocsWithoutFoyer = new ArrayList<>();
        blocsWithoutFoyer.add(bloc);
        when(blocService.trouverBlocsSansFoyer()).thenReturn(blocsWithoutFoyer);

        mockMvc.perform(get("/bloc/trouver-blocs-sans-foyer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$[0].capaciteBloc").value(100));
    }

    @Test
    void testRecuperBlocsParNomEtCap() throws Exception {
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(bloc);
        when(blocService.trouverBlocsParNomEtCap("Bloc A", 100)).thenReturn(blocs);

        mockMvc.perform(get("/bloc/get-bloc-nb-c/{nb}/{c}", "Bloc A", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$[0].capaciteBloc").value(100));
    }
}
