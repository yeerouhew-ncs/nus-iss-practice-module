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
import tbs.tbsapi.domain.enums.EventType;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.dto.EditEventDto;
import tbs.tbsapi.service.EventService;
import tbs.tbsapi.validation.ValidationError;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;
import tbs.tbsapi.vo.response.AddEventResponse;
import tbs.tbsapi.vo.response.GetEventResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
public class EventManager {
    @Autowired
    private EventService eventService;

    public ResponseEntity<?> addEvent(AddEventDto addEventDto) {
        List<ValidationError> validationErrorList = addEventDto.validate();

        if(!validationErrorList.isEmpty()) {
            log.info("END: ADD EVENT VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }

        AddEventResponse addEventResponse = new AddEventResponse();
        if(addEventDto.getEventType().equals(EventType.CONCERT)) {
            addEventResponse = eventService.addConcert(addEventDto);
        } else if (addEventDto.getEventType().equals(EventType.SPORTS)) {
            addEventResponse = eventService.addSportsEvent(addEventDto);
        }

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
        List<ValidationError> validationErrorList = getListOfEventRequest.validate();

        if(!validationErrorList.isEmpty()) {
            log.info("END: GET ALL EVENTS VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }

        Pageable pageable = PageRequest.of(getListOfEventRequest.getPage(), 10, Sort.by("eventFromDt", "eventToDt",  "eventName").ascending());
        Page<GetEventResponse> response = eventService.getListOfEvents(pageable, getListOfEventRequest);

        log.info("response: {} ", response);
        log.info("END: GET ALL EVENTS SUCCESSFUL");
        return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                "statusCode","200",
                "message", "SUCCESS",
                "eventList", response
        ));
    }

    public ResponseEntity<?> getEventDetails(GetEventRequest getEventRequest) {
        GetEventResponse eventResponse = eventService.getEventDetails(getEventRequest);
        log.info("END: GET EVENT DETAILS SUCCESS");

        if(eventResponse.getEventId() != null) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "SUCCESS",
                    "eventDetails", eventResponse
            ));
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", "200",
                    "message", "NO MATCHING EVENT"
            ));
        }
    }

    public ResponseEntity<?> editEvent(EditEventDto editEventDto) {
        List<ValidationError> validationErrorList = editEventDto.validate();

        if(!validationErrorList.isEmpty()) {
            log.info("END: EDIT EVENT VALIDATION FAILED " + validationErrorList);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", "422",
                    "message", "VALIDATION FAILED",
                    "validationError", validationErrorList
            ));
        }

        List<String> response = eventService.editEvent(editEventDto);
        if(Objects.equals(response.get(0), "200")) {
            log.info("END: EDIT EVENT SUCCESS");
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", response.get(0),
                    "message", "SUCCESS"));
        } else {
            log.info("END: EDIT EVENT FAILED");
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of(
                    "statusCode", response.get(0),
                    "message", response.get(1)));
        }
    }
}
