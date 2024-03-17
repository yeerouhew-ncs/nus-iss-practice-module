package tbs.tbsapi.repository;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.domain.Venue;

import java.util.List;

@Repository
@NonNullApi
public interface VenueRepository extends JpaRepository<Venue, String> {
    Venue findByVenueId(Integer venueId);

    List<Venue> findAll();
}
