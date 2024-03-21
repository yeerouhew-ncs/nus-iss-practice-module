package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Seat;
import tbs.tbsapi.domain.Venue;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    @Modifying
    @Query("UPDATE Seat s SET s.seatName= :seatName, s.seatStatus = :seatStatus, s.sectionId = :sectionId " +
            "WHERE s.seatId = :seatId")
    Integer updateSeat(@Param("seatId") Integer seatId,
                       @Param("seatName") String seatName,
                       @Param("seatStatus") String seatStatus,
                       @Param("sectionId") Integer sectionId);

    Integer deleteBySectionId(Integer sectionId);
}
