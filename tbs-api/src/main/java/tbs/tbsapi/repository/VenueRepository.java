package tbs.tbsapi.repository;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tbs.tbsapi.domain.Venue;
import tbs.tbsapi.vo.response.GetVenueResponse;

import java.util.List;

@Repository
@NonNullApi
public interface VenueRepository extends JpaRepository<Venue, String> {
    Venue findByVenueId(Integer venueId);

    List<Venue> findAll();


    @Query("SELECT NEW tbs.tbsapi.vo.response.GetVenueResponse(v.venueId,v.venueName,v.address) " +
            "FROM Venue v " +
            "WHERE (:address IS NULL OR UPPER(v.address) LIKE CONCAT('%', UPPER(:address), '%')) " +
            "AND (:venueName IS NULL OR UPPER(v.venueName) LIKE CONCAT('%', UPPER(:venueName), '%'))"
    )
    List<GetVenueResponse> searchBy(@Param("address") String address, @Param("venueName") String venueName );

}
