package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.service.EventService;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.GetEventResponse;

import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
public class EventManager {
    @Autowired
    private EventService eventService;

    public ResponseEntity<?> addEvent(AddEventDto addEventDto) {
        // TODO: include validation here
        AddEventResponse addEventResponse = eventService.addEvent(addEventDto);
        if(Objects.equals(addEventResponse.getStatusCode(), "200") && Objects.equals(addEventResponse.getMessage(), "SUCCESS")) {
            log.info("END: ADD EVENT SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", addEventResponse.getStatusCode(),
                    "message", "SUCCESS"));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "statusCode", "409",
                "message", "FAILURE"));
    }

    public ResponseEntity<?> getListOfEvents(GetListOfEventRequest getListOfEventRequest) {
        // TODO: include validation here
        Pageable pageable = PageRequest.of(getListOfEventRequest.getPage(), 10, Sort.by("eventFromDt", "eventToDt",  "eventName").ascending());
        Page<GetEventResponse> response = eventService.getListOfEvents(pageable, getListOfEventRequest);

        log.info("END: GET ALL EVENTS SUCCESSFUL");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode",200,
                "message", "SUCCESS",
                "eventList", response
        ));
    }

    public ResponseEntity<?> getEventDetails(GetEventRequest getEventRequest) {
        // TODO: include validation here
        GetEventResponse eventResponse = eventService.getEventDetails(getEventRequest);
        log.info("END: GET EVENT DETAILS SUCCESS");

        if(eventResponse.getEventId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", 200,
                    "message", "SUCCESS",
                    "eventDetails", eventResponse
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", 200,
                    "message", "NO MATCHING EVENT"
            ));
        }
    }
}
