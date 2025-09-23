package com.reservations.repository;

import com.reservations.model.*;
import org.junit.jupiter.api.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservationRepositoryTest {

    @DisplayName("Reservaciones que deberia retornarme la informacion")
    @Test
    void getReservation_should_return_the_information() {
        // Given
        ReservationRepository repository = new ReservationRepository();
        // When
        Optional<Reservation> result = repository.getReservationById(1L);
        // Then
        assertEquals(1L, result.get().getId());
    }

    @DisplayName("Reservaciones que no deberia retornarme la informacion")
    @Test
    void getReservation_should_not_return_the_information() {
        // Given
        ReservationRepository repository = new ReservationRepository();
        // When
        Optional<Reservation> result = repository.getReservationById(6L);
        // Then
        // assertTrue(result.isEmpty());
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isEmpty()));
    }

    @DisplayName("Reservaciones que deberia retornarme toda la informacion")
    @Test
    void getReservation_should_return_all_the_information() {
        // Given
        ReservationRepository repository = new ReservationRepository();
        // When
        Optional<Reservation> result = repository.getReservationById(1L);
        // Then
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                // validar el objeto en su conjunto
                () -> assertEquals(getreservation(1L, "EZE", "MIA"), result.get()));
    }

    private Reservation getreservation(Long id, String origin, String destination) {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Andres");
        passenger.setLastName("Sacco");
        passenger.setId(1L);
        passenger.setDocumentType("DNI");
        passenger.setDocumentNumber("12345678");
        passenger.setBirthday(LocalDate.of(1985, 1, 1));

        Price price = new Price();
        price.setBasePrice(BigDecimal.ONE);
        price.setTotalTax(BigDecimal.ZERO);
        price.setTotalPrice(BigDecimal.ONE);

        Segment segment = new Segment();
        segment.setArrival("2025-01-01");
        segment.setDeparture("2024-12-31");
        segment.setOrigin("EZE");
        segment.setDestination("MIA");
        segment.setCarrier("AA");
        segment.setId(1L);

        Itinerary itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setPrice(price);
        itinerary.setSegment(List.of(segment));

        Reservation reservation = new Reservation();
        reservation.setId(1L);
        reservation.setPassengers(List.of(passenger));
        reservation.setItinerary(itinerary);
        return reservation;

    }

}