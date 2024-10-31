package tn.esprit.tpfoyer.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(UniversiteRestController.class)
class UniversiteRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUniversiteService universiteService;

    private Universite universite;

    @BeforeEach
    void setUp() {
        universite = new Universite(1L, "University A", "123 Street", null);
    }

    @Test
    void testGetUniversites() throws Exception {
        when(universiteService.retrieveAllUniversites()).thenReturn(List.of(universite));

        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomUniversite").value("University A"));
    }

    @Test
    void testRetrieveUniversite() throws Exception {
        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);

        mockMvc.perform(get("/universite/retrieve-universite/{universite-id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("University A"));
    }

    @Test
    void testAddUniversite() throws Exception {
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/add-universite")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("University A"));
    }

    @Test
    void testRemoveUniversite() throws Exception {
        doNothing().when(universiteService).removeUniversite(1L);

        mockMvc.perform(delete("/universite/remove-universite/{universite-id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyUniversite() throws Exception {
        universite.setNomUniversite("University B");
        when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(put("/universite/modify-universite")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomUniversite").value("University B"));
    }
}
