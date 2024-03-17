package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SectionSeat;

import java.util.List;

@Repository
public interface SectionSeatRepository extends JpaRepository<SectionSeat, String> {
    List<SectionSeat> findAllByPlanId(Integer planId);
}
