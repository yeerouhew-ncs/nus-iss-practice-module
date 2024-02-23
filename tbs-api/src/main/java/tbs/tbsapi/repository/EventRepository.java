package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
//    Event findByEventId(Integer eventId);
}
