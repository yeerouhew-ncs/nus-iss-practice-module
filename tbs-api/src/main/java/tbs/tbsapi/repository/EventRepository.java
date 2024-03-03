package tbs.tbsapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Event;
import tbs.tbsapi.vo.response.GetEventResponse;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
    @Query("SELECT NEW tbs.tbsapi.vo.response.GetEventResponse(" +
            "e.eventId, e.eventName, e.artistName, e.eventFromDt, e.eventToDt, e.subjectId, e.planId) " +
            "FROM Event e " +
            "WHERE (:eventId IS NULL OR e.eventId = :eventId) AND " +
            "(:eventName IS NULL OR UPPER(e.eventName) LIKE CONCAT('%', UPPER(:eventName), '%')) AND " +
            "(:artistName IS NULL OR UPPER(e.artistName) = UPPER(:artistName)) AND " +
            "(:eventFromDt IS NULL OR e.eventFromDt >= :eventFromDt) AND" +
            "(:eventToDt IS NULL OR e.eventToDt <= :eventToDt) AND " +
            "(:subjectId IS NULL OR e.subjectId = :subjectId) "
    )
    Page<GetEventResponse> findEventList(
            @Param("eventId") Integer eventId,
            @Param("eventName") String eventName,
            @Param("artistName") String artistName,
            @Param("eventFromDt") LocalDateTime eventFromDt,
            @Param("eventToDt") LocalDateTime eventToDt,
            @Param("subjectId") Integer subjectId,
            Pageable pageable
    );

    Event findByEventId(Integer eventId);

    @Modifying
    @Query("UPDATE Event e SET e.eventName = :eventName, e.artistName = :artistName, e.eventFromDt = :eventFromDt, " +
            "e.eventToDt = :eventToDt, e.planId = :planId, e.subjectId = :subjectId WHERE e.eventId = :eventId")
    Integer updateUser(@Param("eventId") Integer eventId,
                       @Param("eventName") String eventName,
                       @Param("artistName") String artistName,
                       @Param("eventFromDt") LocalDateTime eventFromDt,
                       @Param("eventToDt") LocalDateTime eventToDt,
                       @Param("planId") Integer planId,
                       @Param("subjectId") Integer subjectId);

}
