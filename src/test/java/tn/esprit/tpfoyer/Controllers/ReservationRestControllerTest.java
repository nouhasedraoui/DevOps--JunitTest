package tn.esprit.tpfoyer.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.tpfoyer.control.ReservationRestController;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@WebMvcTest(ReservationRestController.class)
class ReservationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IReservationService reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation("R1", new Date(), true, null);
    }

    @Test
    void testGetReservations() throws Exception {
        when(reservationService.retrieveAllReservations()).thenReturn(List.of(reservation));

        mockMvc.perform(get("/reservation/retrieve-all-reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReservation").value("R1"));
    }

    @Test
    void testRetrieveReservation() throws Exception {
        when(reservationService.retrieveReservation("R1")).thenReturn(reservation);

        // Ensure correct path variable name
        mockMvc.perform(get("/reservation/retrieve-reservation/{reservation-id}", "R1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("R1"));
    }

    @Test
    void testAddReservation() throws Exception {
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservation/add-reservation")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("R1"));
    }

    @Test
    void testRemoveReservation() throws Exception {
        doNothing().when(reservationService).removeReservation("R1");

        mockMvc.perform(delete("/reservation/remove-reservation/{reservation-id}", "R1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyReservation() throws Exception {
        when(reservationService.modifyReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/reservation/modify-reservation")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservation)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("R1"));
    }

    @Test
    void testRetrieveReservationsByDateAndStatus() throws Exception {
        Date date = new Date();
        when(reservationService.trouverResSelonDateEtStatus(any(Date.class), eq(true)))
                .thenReturn(List.of(reservation));

        // Format date to match @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);

        mockMvc.perform(get("/reservation/retrieve-reservation-date-status/{d}/{v}", formattedDate, true))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReservation").value("R1"));
    }
}
