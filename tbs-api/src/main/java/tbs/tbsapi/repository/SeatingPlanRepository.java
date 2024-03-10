package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SeatingPlan;

import java.util.List;

@Repository
public interface SeatingPlanRepository extends JpaRepository<SeatingPlan, String> {
    SeatingPlan findByPlanId(Integer planId);

    List<SeatingPlan> findAll();
}
