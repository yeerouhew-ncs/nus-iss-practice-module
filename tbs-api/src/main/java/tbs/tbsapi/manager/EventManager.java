package tbs.tbsapi.manager;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tbs.tbsapi.dto.AddEventDto;
import tbs.tbsapi.service.EventService;
import tbs.tbsapi.vo.response.AddEventResponse;

import java.util.Map;
import java.util.Objects;

@Log4j2
@Service
public class EventManager {
    @Autowired
    private EventService eventService;

    public ResponseEntity<?> addEvent(AddEventDto addEventDto) {
        // TODO: have validation here
        AddEventResponse addEventResponse = eventService.addEvent(addEventDto);
        if(Objects.equals(addEventResponse.getStatusCode(), "200") && Objects.equals(addEventResponse.getMessage(), "SUCCESS")) {
            return ResponseEntity.status(HttpStatus.OK).body(Map.of(
                    "statusCode", addEventResponse.getStatusCode(),
                    "message", "SUCCESS"));
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "statusCode", "409",
                "message", "FAILURE"));
    }
}
