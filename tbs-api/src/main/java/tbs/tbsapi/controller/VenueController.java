package tbs.tbsapi.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.manager.VenueManager;
import tbs.tbsapi.vo.request.GetVenueRequest;

@Log4j2
@RestController
@RequestMapping("api/venue")
@CrossOrigin(origins = "*")
public class VenueController {
    @Autowired
    VenueManager venueManager;

    @PostMapping(path = "/venue-details")
    public ResponseEntity<?> getVenueDetails(@RequestBody GetVenueRequest venueRequest) {
        log.info("START: GET VENUE DETAILS");
        return venueManager.getVenueDetails(venueRequest);
    }

    @PostMapping(path = "/get-list")
    public ResponseEntity<?> getVenueList() {
        log.info("START: GET LIST OF VENUES");
        return venueManager.getVenueList();
    }
}
