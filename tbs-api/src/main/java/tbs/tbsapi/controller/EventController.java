package tbs.tbsapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.manager.EventManager;

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

//    @PostMapping(path = "/get-all")
//    public ResponseEntity<?> getAllEvents() throws Exception{
//        log.info("START: GET ALL EVENTS");
//        return eventManager.addEvent(addEventDto);
//    }
}
