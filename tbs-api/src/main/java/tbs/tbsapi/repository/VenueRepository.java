package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.SeatingPlan;
import tbs.tbsapi.domain.Venue;

@Repository
public interface VenueRepository extends JpaRepository<Venue, String> {
    Venue findByVenueId(Integer venueId);

}
