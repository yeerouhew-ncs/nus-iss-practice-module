package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SeatingPlan;

import java.util.List;

@Repository
public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, String> {
    SeatingPlan findByPlanId(Integer planId);

    List<SeatingPlan> findAll();

    @Modifying
    @Query("UPDATE SeatingPlan s SET s.planName= :planName, s.planRow = :planRow, s.planCol = :planCol, " +
            "s.venueId = :venueId WHERE s.planId = :planId")
    Integer updatePlan(@Param("planId") Integer planId,
                       @Param("planName") String planName,
                       @Param("planRow") Integer planRow,
                       @Param("planCol") Integer planCol,
                       @Param("venueId") Integer venueId);
}
