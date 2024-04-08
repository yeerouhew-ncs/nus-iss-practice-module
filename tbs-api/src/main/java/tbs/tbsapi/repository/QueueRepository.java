package tbs.tbsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tbs.tbsapi.domain.Queue;
import tbs.tbsapi.domain.enums.QueueStatus;

@Repository
public interface QueueRepository extends JpaRepository<Queue, String> {
    Queue findByQueueId(Integer queueId);
    Queue findByTicketnumber(String ticketnumber);

    @Query("SELECT q FROM Queue q " +
            "WHERE q.eventId= :eventId " +
            "AND q.subjectId = :subjectId "+
            "AND q.ticketnumber = :ticketnumber "
    )
    Queue findByIds(@Param("eventId") int eventId ,
                    @Param("subjectId") int subjectId ,
                    @Param("ticketnumber") String ticketnumber );


    @Modifying
    @Query("UPDATE Queue q " +
            "SET q.queueStatus= :queueStatus " +
            "WHERE q.ticketnumber = :ticketnumber")
    Integer updateQueueStatusByTicketNumber(@Param("queueStatus") String queueStatus ,
                                            @Param("ticketnumber") String ticketnumber );
    @Modifying
    @Query("UPDATE Queue q " +
            "SET q.queueStatus= :queueStatus " +
            "WHERE q.queueId = :queueId ")
    Integer updateQueueStatusByQueueId(@Param("queueStatus") String queueStatus ,
                                       @Param("queueId") int queueId );

    @Modifying
    @Query("UPDATE Queue q " +
            "SET q.queueStatus= :queueStatus " +
            "WHERE q.eventId= :eventId " +
            "AND q.subjectId = :subjectId "+
            "AND q.ticketnumber = :ticketnumber ")
    Integer updateQueueStatusByIDs(@Param("queueStatus") String queueStatus ,
                                   @Param("eventId") int eventId, @Param("subjectId") int subjectId ,
                                   @Param("ticketnumber") String ticketnumber );

}
