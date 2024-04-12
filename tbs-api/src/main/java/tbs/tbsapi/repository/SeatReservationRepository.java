package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SeatReservation;

import java.util.List;

@Repository
public interface SeatReservationRepository extends JpaRepository<SeatReservation, String> {
    @Query("SELECT sr FROM SeatReservation sr " +
            "LEFT JOIN Order o ON sr.orderId = o.orderId " +
            "LEFT JOIN Event e ON o.eventId = e.eventId " +
            "WHERE o.eventId = :eventId AND sr.sectionId = :sectionId")
    List<SeatReservation> findSeatReservationByEventId(@Param("eventId") int eventId, @Param("sectionId") int sectionId);
}
