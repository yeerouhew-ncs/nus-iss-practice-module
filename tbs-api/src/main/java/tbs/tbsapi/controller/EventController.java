package tbs.tbsapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.manager.EventManager;
import tbs.tbsapi.vo.request.GetEventRequest;
import tbs.tbsapi.vo.request.GetListOfEventRequest;

@Log4j2
@RestController
@RequestMapping("api/event")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventManager eventManager;

    @PostMapping(path = "/add")
    public ResponseEntity<?> addEvent(@RequestBody AddEventDto addEventDto) throws Exception{
        log.info("START: ADD EVENT");
        return eventManager.addEvent(addEventDto);
    }

    @PostMapping(path = "/get-list")
    public ResponseEntity<?> getListOfEvents(@RequestBody GetListOfEventRequest getListOfEventRequest) throws Exception{
        log.info("START: GET ALL EVENTS");
        return eventManager.getListOfEvents(getListOfEventRequest);
    }

    @PostMapping(path = "/event-details")
    public ResponseEntity<?> getEventDetails(@RequestBody GetEventRequest getEventRequest) throws Exception{
        log.info("START: GET EVENT DETAILS");
        return eventManager.getEventDetails(getEventRequest);
    }
}
