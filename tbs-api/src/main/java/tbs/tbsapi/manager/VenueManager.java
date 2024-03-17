package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.service.VenueService;
import tbs.tbsapi.vo.request.GetVenueRequest;
import tbs.tbsapi.vo.response.GetVenueResponse;

import java.util.List;
import java.util.Map;

@Log4j2
@Service
public class VenueManager {
    @Autowired
    VenueService venueService;

    public ResponseEntity<?> getVenueDetails(GetVenueRequest venueRequest) {
        GetVenueResponse venueResponse = venueService.getVenueDetails(venueRequest);
        log.info("END: GET VENUE DETAILS SUCCESSFUL");
        if(venueResponse.getVenueId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "SUCCESS",
                    "venueDetails", venueResponse
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "NO MATCHING VENUE"
            ));
        }
    }

    public ResponseEntity<?> getVenueList() {
        List<GetVenueResponse> venueResponseList = venueService.getVenueList();
        log.info("END: GET LIST OF VENUES SUCCESSFUL");

        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode", "200",
                "message", "SUCCESS",
                "venueList", venueResponseList
        ));
    }

}
