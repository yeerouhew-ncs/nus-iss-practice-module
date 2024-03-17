package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SectionSeat;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SectionSeatRepository extends JpaRepository<SectionSeat, String> {
    List<SectionSeat> findAllByPlanId(Integer planId);

    @Modifying
    @Query("UPDATE SectionSeat s SET s.totalSeats= :totalSeats, s.noSeatsLeft = :noSeatsLeft, s.seatPrice = :seatPrice, " +
            "s.seatSectionDescription = :seatSectionDescription, s.sectionRow = :sectionRow, s.sectionCol = :sectionCol, " +
            "s.planId = :planId " +
            "WHERE s.sectionId = :sectionId")
    Integer updateSectionSeat(@Param("sectionId") Integer sectionId,
                       @Param("planId") Integer planId,
                       @Param("totalSeats") Integer totalSeats,
                       @Param("noSeatsLeft") Integer noSeatsLeft,
                       @Param("seatPrice") Double seatPrice,
                       @Param("seatSectionDescription") String seatSectionDescription,
                       @Param("sectionRow") Integer sectionRow,
                       @Param("sectionCol") Integer sectionCol);
}
