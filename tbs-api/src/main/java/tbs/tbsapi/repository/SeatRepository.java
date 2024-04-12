package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Seat;
import tbs.tbsapi.domain.SectionSeat;
import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.domain.enums.SeatStatus;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, String> {
    @Modifying
    @Query("UPDATE Seat s SET s.seatName= :seatName, s.seatRow= :seatRow, s.seatCol= :seatCol, s.seatStatus = :seatStatus, s.sectionId = :sectionId " +
            "WHERE s.seatId = :seatId")
    Integer updateSeat(@Param("seatId") Integer seatId,
                       @Param("seatName") String seatName,
                       @Param("seatRow") Integer seatRow,
                       @Param("seatCol") Integer seatCol,
                       @Param("seatStatus") SeatStatus   seatStatus,
                       @Param("sectionId") Integer sectionId);

    @Query("UPDATE Seat s SET s.seatStatus='reserved'" +
            "WHERE s.seatName in :seatNames and s.sectionId in :sectionIds")
    Integer updateSeatsReserved(@Param("seatNames") List<String> seatNames, @Param("sectionIds") List<Integer> sectionIds);

    Integer deleteBySectionId(Integer sectionId);

    List<Seat> findAllBySectionId(Integer sectionId);

    List<Seat> findAll();
}
