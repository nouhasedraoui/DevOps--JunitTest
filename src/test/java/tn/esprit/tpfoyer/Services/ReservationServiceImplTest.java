package tn.esprit.tpfoyer.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation("R1", new Date(), true, null);
    }

    @Test
    void testRetrieveAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.retrieveAllReservations();

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        when(reservationRepository.findById("R1")).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.retrieveReservation("R1");

        assertNotNull(foundReservation);
        assertEquals("R1", foundReservation.getIdReservation());
        verify(reservationRepository, times(1)).findById("R1");
    }

    @Test
    void testAddReservation() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationService.addReservation(reservation);

        assertNotNull(savedReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testModifyReservation() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation updatedReservation = reservationService.modifyReservation(reservation);

        assertNotNull(updatedReservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testRemoveReservation() {
        doNothing().when(reservationRepository).deleteById("R1");

        reservationService.removeReservation("R1");

        verify(reservationRepository, times(1)).deleteById("R1");
    }

    @Test
    void testFindReservationsByDateAndStatus() {
        Date date = new Date();
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, true))
                .thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.trouverResSelonDateEtStatus(date, true);

        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, true);
    }
}
